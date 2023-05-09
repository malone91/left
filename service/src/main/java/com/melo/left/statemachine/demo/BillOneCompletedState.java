package com.melo.left.statemachine.demo;

import com.melo.left.statemachine.State;

public class BillOneCompletedState<T extends BaseBill> implements State<T> {

    @Override
    public int getId() {
        return BillOneStateEnum.Completed.getId();
    }

    @Override
    public String getName() {
        return BillOneStateEnum.Completed.name();
    }
}
