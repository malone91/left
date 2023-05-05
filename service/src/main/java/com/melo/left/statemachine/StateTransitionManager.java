package com.melo.left.statemachine;

public interface StateTransitionManager<T extends StateHolder> {

    Event registerStateTransitionPair(State from, State to);
}
