package com.melo.left.statemachine.demo;

import com.melo.left.statemachine.StateHolder;
import lombok.Data;

/**
 * 一个普通的工单
 */
@Data
public class BillOne extends BaseBill {

    private String specialAttribute;
}
