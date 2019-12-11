package com.zoe.diary.listener;

/**
 * author zoe
 * created 2019/12/11 14:59
 */

public interface ItemTouchMoveListener {

    /**
     * 当拖拽的时候回调
     * 可以在此方法里面实现：拖拽条目并实现刷新效果
     * fromPosition 从什么位置拖
     * toPosition	到什么位置
     * 是否执行了move
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * 当条目被移除是回调
     * position 移除的位置
     */
    boolean onItemRemove(int position);
}
