package com.zoe.diary.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoe.diary.R;

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
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {
        helper.setText(R.id.tv_day, item > 0 ? String.valueOf(item) : "");
        helper.getView(R.id.tv_day).setEnabled(item > 0);
        helper.getView(R.id.tv_day).setTag(String.valueOf(item));
        helper.addOnClickListener(R.id.tv_day);
    }
}
