package com.melo.left.limiter;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 需要动态配置
 *
 * 限流在于限制流量，也就是QPS或者线程的并发数，请求处理不稳定或者服务损坏，
 * 导致请求处理时间过长或者老是频繁抛出异常，这时就需要对服务进行降级处理
 * 所谓的降级处理和限流处理在形式上没有明显差异，也是以同样的形式定义一个临界区，
 * 区别是需要对抛出来的异常需要进行统计，这样才可以知道请求异常的频率，有了这个指标才会触发降级。
 *
 * 触发限流时会抛出 FlowException，触发熔断时会抛出 DegradeException，这两个异常都继承自 BlockException。
 */
public class SentinelByExceptionDemo {

    public static void main(String[] args) {
        String resourceName = "helloCode";
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(resourceName);
        rule.setCount(1);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        //load rule
        FlowRuleManager.loadRules(rules);

        //run resource(code snippet) protected
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry(resourceName);
                System.out.println("hello world");
            } catch (BlockException e) {
                System.out.println("blocked");
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
