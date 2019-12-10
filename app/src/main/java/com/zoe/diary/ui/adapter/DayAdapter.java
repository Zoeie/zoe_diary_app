package com.zoe.diary.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoe.diary.R;

import java.util.Calendar;
import java.util.List;

/**
 * author zoe
 * created 2019/12/6 17:25
 */

public class DayAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private Context context;

    public DayAdapter(@Nullable List<Integer> data, Context context) {
        super(R.layout.diary_calendar, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer value) {
        helper.setText(R.id.tv_day, value > 0 ? String.valueOf(value) : "");
        helper.getView(R.id.tv_day).setEnabled(value > 0);
        helper.getView(R.id.tv_day).setTag(value);
        if (helper.getAdapterPosition() % Calendar.DAY_OF_WEEK == 0) {
            helper.setTextColor(R.id.tv_day, context.getResources().getColor(R.color.colorAccent));
        } else if ((helper.getAdapterPosition() + 1) % Calendar.DAY_OF_WEEK == 0) {
            helper.setTextColor(R.id.tv_day, context.getResources().getColor(R.color.colorPrimary));
        } else {
            helper.setTextColor(R.id.tv_day, context.getResources().getColor(R.color.color_black));
        }
        helper.addOnClickListener(R.id.tv_day);
    }
}
