package com.melo.left.limiter;

import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SentinelByBoolDemo {

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

        while (true) {
            if (SphO.entry(resourceName)) {
                try {
                    System.out.println("Hello world");
                } finally {
                    SphO.exit();
                }
            } else {
                System.out.println("blocked");
            }

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
