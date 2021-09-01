package com.melo.left.limiter.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
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
        //资源、规则
        rule.setCount(2);
        rule.setResource(resourceName);
        //限流规则类型 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
