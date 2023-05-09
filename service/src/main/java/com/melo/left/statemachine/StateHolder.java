package com.melo.left.statemachine;

/**
 * 持有状态属性和支持状态转换的对象
 */
public interface StateHolder {

    <T extends StateHolder> State<T> getState();

    <T extends StateHolder> void setState(State<T> state);
}
