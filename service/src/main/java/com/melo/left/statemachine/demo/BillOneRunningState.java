package com.melo.left.statemachine.demo;

import com.melo.left.statemachine.State;

public class BillOneRunningState<T extends BaseBill> implements State<T> {

    @Override
    public int getId() {
        return BillOneStateEnum.Running.getId();
    }

    @Override
    public String getName() {
        return BillOneStateEnum.Running.name();
    }
}
