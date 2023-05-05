package com.melo.left.statemachine;

/**
 * 每一个 StateHolder 对象所 具有的所有State 和 能够单向State转换的Pair<from, to>集合
 * @param <T>
 */
public interface StateMachineRegistry<T extends StateHolder> {

    boolean registerTransition(Event event, State from, State to, Action action);


    void supportState(State state);

}
