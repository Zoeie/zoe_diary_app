package com.zoe.diary.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.zoe.diary.R;
import com.zoe.diary.app.DiaryApplication;
import com.zoe.diary.listener.ItemTouchMoveListener;

/**
 * author zoe
 * created 2019/12/11 15:00
 */

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchMoveListener moveListener;

    public MyItemTouchHelperCallback(ItemTouchMoveListener moveListener) {
        this.moveListener = moveListener;
    }

    //Callback回调监听时先调用的，用来判断当前是什么动作，比如判断方向（意思就是我要监听哪个方向的拖动）
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        //需要监听的拖拽方向是哪两个方向
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //我要监听的swipe侧滑方向是哪个方向
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 是否允许长按拖拽效果
        return false;
    }

    //当移动的时候回调的方法--拖拽
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
        if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) {
            return false;
        }
        // 在拖拽的过程当中不断地调用adapter.notifyItemMoved(from,to);
        boolean result = moveListener.onItemMove(srcHolder.getAdapterPosition(), targetHolder.getAdapterPosition());
        return result;
    }

    //侧滑的时候回调的
    @Override
    public void onSwiped(RecyclerView.ViewHolder holder, int arg1) {
        // 监听侧滑，1.删除数据；2.调用adapter.notifyItemRemove(position)
        moveListener.onItemRemove(holder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //判断选中状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 恢复
        viewHolder.itemView.setBackgroundColor(DiaryApplication.getInstance().getResources().getColor(R.color.color_dbdbdb));
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                            boolean isCurrentlyActive) {
        //dX:水平方向移动的增量（负：往左；正：往右）范围：0~View.getWidth  0~1
        float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //透明度动画
            viewHolder.itemView.setAlpha(alpha);//1~0
            viewHolder.itemView.setScaleX(alpha);//1~0
            viewHolder.itemView.setScaleY(alpha);//1~0
        }

        //删掉一个条目之后，恢复原状
        if (alpha == 0) {
            viewHolder.itemView.setAlpha(1);//1~0
            viewHolder.itemView.setScaleX(1);//1~0
            viewHolder.itemView.setScaleY(1);//1~0
        }
        //此super方法会自动处理setTranslation
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}
