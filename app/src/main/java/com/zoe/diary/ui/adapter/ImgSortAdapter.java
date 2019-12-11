package com.zoe.diary.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoe.diary.R;
import com.zoe.diary.listener.ItemTouchMoveListener;
import com.zoe.diary.listener.StartDragListener;
import com.zoe.diary.utils.LogUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * author zoe
 * created 2019/12/6 17:25
 */

public class ImgSortAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements ItemTouchMoveListener {

    private Context context;
    private StartDragListener startDragListener;

    public ImgSortAdapter(@Nullable List<String> data, Context context, StartDragListener listener) {
        super(R.layout.diary_img_sort, data);
        this.context = context;
        this.startDragListener = listener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.diary_img);
        ImageView iv = helper.getView(R.id.diary_img);
        Glide.with(context).load(item).centerCrop().into(iv);
        helper.getView(R.id.iv_change_sort).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //传递触摸情况给谁？
                    startDragListener.onStartDrag(helper);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        // 1.数据交换；2.刷新
        Collections.swap(getData(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public boolean onItemRemove(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
        return true;
    }
}
