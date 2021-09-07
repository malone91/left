package com.melo.left.limiter.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 资源：
 * 抛出异常的方式定义资源
 * 返回布尔值方式定义资源 if else 风格
 * 异步调用支持
 * 注解方式定义资源
 * 主流框架默认配置
 * 规则：
 * 流量控制
 * 熔断降级
 * 系统保护
 * 来源访问控制
 * 动态规则扩展
 *
 * 同一个资源可有多个限流规则，依次检查
 * 熔断降级是在调用链中某个资源出现不稳定状态时，超时或异常比例升高，对这个资源进行限制让请求快速失败，避免影响到
 * 其他资源导致级联错误DegradeException
 *
 * ProcessSlotChain 编排slot
 * 1 NodeSelectorSlot 收集资源调用路径，树状结构存储起来 调用树 一个应用一个root节点
 * 2 ClusterBuilderSlot 存储资源统计信息和调用者信息，RT,QPS,THREAD_COUNT,EXCEPTION count，block count
 * 3 StatisticsSlot 记录、统计实时的不同维度runtime指标监控信息，多维度限流，降级依据。 SlidingWindow-环形数组
 * ParamFlowSlot
 * SystemSlot
 * AuthoritySlot
 * FlowSlot
 * DegradeSlot
 *
 * Context 做数据的传递，处理槽如何进行传递，通过上下文，有数据变更直接扔到上下文里
 *
 * SentinelAutoConfiguration SentinelResourceAspect
 * this.entryWithType(name, resourceType, entryType, count, false, args);
 * count当前请求过来可以增加多少个计数
 * false当前请求不用等待  true当前资源必须等待相应的时间才可通过
 * StringResourceWrapper资源对象
 *
 * 一个请求会占用一个线程，一个线程会绑定一个context
 *
 * 返回一个无需做规则检测的资源操作对象entry
 *
 * context构建时需要node
 * contextNameNodeMap key:context_name,value:node
 *
 * DCL防止并发创建
 *
 * node = new EntranceNode(new StringResourceWrapper(name, EntryType.IN), null);
 // Add entrance node.
 Constants.ROOT.addChild(node);

 Map<String, DefaultNode> newMap = new HashMap<>(contextNameNodeMap.size() + 1);
 newMap.putAll(contextNameNodeMap);
 newMap.put(name, node);
 contextNameNodeMap = newMap;
 为了防止迭代稳定性问题----iterator stable 共享集合的写操作最好采用这种方式，有可能读到脏数据

  FlowRule中的变量解析： controlBehavior  1. warm up 快速失败改进的令牌桶算法, 2. rate limiter排队等待，漏斗算法
   Function#apply

 DefaultController 如果没有规则，返回0
 if (node == null) {
 return DEFAULT_AVG_USED_TOKENS;
 }

 on开头都是回调
 probe invocation 尝试调用
 熔断器的三种状态 打开，关闭，半打开   关闭：所有请求可以通过
 半打开：慢调用可以尝试调用，如果还慢，变成open，拒绝；恢复了，熔断器将变为close状态；
 下一个请求时间窗到了，熔断器成功，则是一个过渡状态 open---> half open  --->open
 boolean tryDoingSth

 滑动时间窗口任意时间段内 100t
 滑动速度的问题  重复统计，带来性能问题 如何解决呢？
 样本窗口，长度25t 单位时间窗样本数4
 数组 每个元素记个数
 DefaultNode#addPassRequest {
    //增加当前入口的DefaultNode中的统计数据
    super.addPassRequest(count);
    //增加当前资源的ClusterNode中的全局统计数据
    this.clusterNode.addPassRequest(count);
 }

 private transient volatile Metric rollingCounterInSecond = new ArrayMetric(
 //样本窗口数量 2
 SampleCountProperty.SAMPLE_COUNT,
 //时间窗长度 1s
 IntervalProperty.INTERVAL);

 ArrayMetric 存放统计数据的计量器 private final LeapArray<MetricBucket> data;
 abstract class LeapArray<T>  MetricBucket
 * Leap array use sliding window algorithm to count data. Each bucket cover {@code windowLengthInMs} time span,
 * and the total time span is {intervalInMs}, so the total bucket amount is:
 * {@code sampleCount = intervalInMs / windowLengthInMs}.
 *
 * LeapArray<T>有个成员变量，AtomicReferenceArray<WindowWrap<T>> array;样本窗口集合 T为MetricBucket 一个就是一个WindowWrap
 *
 *
 * private final long windowLengthInMs; 样本窗口长度
 * private long windowStart; 样本窗口开始时间
 * private T value; 样本窗口中的统计数据，类型为MetricBucket
 *
 * MetricBucket： private final LongAdder[] counters统计维度很多，维度类型在MetricEvent枚举中
 * private volatile long minRt;
 *
 * DefaultNode.addPassRequest
 * int idx = calculateTimeIdx(timeMillis);根据当前时间计算数组LeapArray中的索引
 * long windowStart = calculateWindowStart(timeMillis);计算当前样本窗口的开始时间点
 *
 * 读中间交叉的数量 FlowSlot
 */
@RequestMapping("sentinel/my")
@RestController
public class TestSentinelController {

    String resourceName = "Hello";

    @GetMapping("hello")
    public String hello() {
        try (Entry entry = SphU.entry(resourceName)) {
            return "hello sentinel";
        } catch (BlockException e) {
            e.printStackTrace();
            return "系统繁忙，请稍后";
        }
    }

    /**
     * execute after current class constructor method
     */
    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> rules = Lists.newArrayList();
        FlowRule rule = new FlowRule();
        //资源、规则 QPS或并发线程数
        rule.setCount(2);
        rule.setResource(resourceName);
        //限流规则类型 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 一个context生命周期可以包含多个资源操作，resource1，resource2。。。
     * 如果没有指定生成context则会创建一个name为sentinel_default_context的默认context
     *                                           root
     *                      entranceOne上下文            entranceTwo上下文
     *           defaultNode1 defaultNode2           defaultNode2 defaultNode3
     *
     *
     *              clusterNode1          clusterNode2       clusterNode3
     */
    public void demo() {
        //创建一个来自appA的访问的context， entranceOne为context的name
        ContextUtil.enter("entranceOne", "appA");
        //获取resource1资源的entry，操作对象，令牌，entry的本质是资源操作对象
        Entry resource1 = null;
        Entry resource2 = null;
        try {
            resource1 = SphU.entry("resource1");

            resource2 = SphU.entry("resource2");
        } catch (BlockException e) {
            //请求被限流，执行降级处理
            e.printStackTrace();
        } finally {
            //通过了限流各种
            if (resource1 != null) {
                resource1.exit();
            }
            if (resource2 != null) {
                resource2.exit();
            }
        }
        //清理context，其生命周期结束
        ContextUtil.exit();

        //有重合代码
        //创建一个来自appA的访问的context， entranceOne为context的name
        ContextUtil.enter("entranceTwo", "appA");
        //获取resource1资源的entry，操作对象，令牌，entry的本质是资源操作对象
        Entry resource3 = null;
        try {
            resource2 = SphU.entry("resource2");

            resource3 = SphU.entry("resource3");
        } catch (BlockException e) {
            //请求被限流，执行降级处理
            e.printStackTrace();
        } finally {
            //通过了限流各种
            if (resource2 != null) {
                resource2.exit();
            }
            if (resource3 != null) {
                resource3.exit();
            }
        }
        //清理context，其生命周期结束
        ContextUtil.exit();
    }
}
