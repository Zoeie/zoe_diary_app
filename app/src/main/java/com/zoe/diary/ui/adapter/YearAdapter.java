package com.zoe.diary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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

public class YearAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private Context context;
    private int month;
    private int year;
    private int[] monthArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    public OnDateListener onDateListener;

    public YearAdapter(@Nullable List<Integer> data, Context context, int targetYear, int targetMonth) {
        super(R.layout.diary_year, data);
        this.context = context;
        this.year = targetYear;
        this.month = targetMonth;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer value) {
        helper.setText(R.id.tv_year, String.valueOf(value));
        GridView gridView = helper.getView(R.id.gv_month);
        gridView.setAdapter(new DiaryYearAdapter(context, monthArr, month, value == year));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(onDateListener != null) {
                    onDateListener.onDate(value, monthArr[position]);
                }
            }
        });
    }

    public void setOnDateListener(OnDateListener onDateListener) {
        this.onDateListener = onDateListener;
    }

    public interface OnDateListener {
        //year 2010 - 2023 month 0-11
        void onDate(int year,int month);
    }
}
