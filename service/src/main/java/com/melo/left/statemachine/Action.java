package com.melo.left.statemachine;

/**
 * 转移过程中发生的操作，如果成功则转移成功，如果失败则不改变状态
 */
public interface Action<T extends State> {

    void execute();
}
