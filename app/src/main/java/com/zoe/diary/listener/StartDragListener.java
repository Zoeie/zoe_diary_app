package com.zoe.diary.listener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * author zoe
 * created 2019/12/11 15:03
 */
public interface StartDragListener {

    /**
     * 该接口用于需要主动回调拖拽效果的
     *
     * @param viewHolder
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
