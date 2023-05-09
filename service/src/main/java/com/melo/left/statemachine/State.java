package com.melo.left.statemachine;

/**
 * 状态本身
 */
public interface State<T extends StateHolder> {

    int getId();

    String getName();

    /**
     * context状态改变之前执行的动作 可能是点击一下状态就变更了或者有其他的执行逻辑
     * @param context
     */
    default void beforeChange(T context) {

    }

    /**
     * context状态改变之后执行的动作 可能是点击一下状态就变更了或者有其他的执行逻辑
     * @param context
     */
    default void afterChange(T context) {

    }
}
