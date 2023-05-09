package com.melo.left.statemachine.demo;

import com.melo.left.statemachine.State;
import com.melo.left.statemachine.StateHolder;
import lombok.Data;


@Data
public class BaseBill implements StateHolder {

    private Long id;
    private String name;
    private String stateName;

    @Override
    public <T extends StateHolder> State<T> getState() {
        return null;
    }

    @Override
    public <T extends StateHolder> void setState(State<T> state) {

    }
}
