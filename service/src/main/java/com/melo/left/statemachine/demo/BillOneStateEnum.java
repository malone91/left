package com.melo.left.statemachine.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 示例工单的状态枚举
 */
@Getter
@AllArgsConstructor
public enum BillOneStateEnum {

    Init(1),
    Running(2),
    Completed(3);

    private Integer id;
}
