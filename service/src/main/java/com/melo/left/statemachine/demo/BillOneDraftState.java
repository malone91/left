package com.melo.left.statemachine.demo;

import com.melo.left.statemachine.State;

public class BillOneDraftState<T extends BaseBill> implements State<T> {

    @Override
    public int getId() {
        return BillOneStateEnum.Init.getId();
    }

    @Override
    public String getName() {
        return BillOneStateEnum.Init.name();
    }

    @Override
    public void beforeChange(T context) {
        System.out.println(context.getName() + "单子正在创建中");
    }

    @Override
    public void afterChange(T context) {
        System.out.println(context.getName() + "单子已经创建完成");
    }
}
