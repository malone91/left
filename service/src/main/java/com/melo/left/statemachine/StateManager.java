package com.melo.left.statemachine;

public interface StateManager<T extends StateHolder> {

    State<T> getStateById(int id);

    void registerState(State<T> state);
}
