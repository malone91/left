package com.melo.left.statemachine;

/**
 * 监听状态转移，主要是 在要转换前和转换成功后发生的逻辑
 */
public interface StateTransitionListener {

    void before();

    void after();
}
