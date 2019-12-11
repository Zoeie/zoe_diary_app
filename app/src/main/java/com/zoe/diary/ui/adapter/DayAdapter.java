package com.zoe.diary.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoe.diary.R;
import com.zoe.diary.database.DbManager;
import com.zoe.diary.database.domain.DiaryInfo;
import com.zoe.diary.utils.LogUtil;

import java.util.Calendar;
import java.util.List;

/**
 * author zoe
 * created 2019/12/6 17:25
 */

public class DayAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private Context context;
    private int year;
    private int month;
    private int currYear;
    private int currMonth;
    private int currDay;
    private List<DiaryInfo> diaryByMonth;

    public DayAdapter(@Nullable List<Integer> data, Context context, int year, int month) {
        super(R.layout.diary_calendar, data);
        this.context = context;
        this.year = year;
        this.month = month;
        Calendar instance = Calendar.getInstance();
        currYear = instance.get(Calendar.YEAR);
        currMonth = instance.get(Calendar.MONTH);
        currDay = instance.get(Calendar.DAY_OF_MONTH);
        diaryByMonth = DbManager.getInstance().getDiaryByMonth(year, month);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer value) {
        helper.setText(R.id.tv_day, value > 0 ? String.valueOf(value) : "");
        helper.getView(R.id.tv_day).setEnabled(value > 0);
        helper.getView(R.id.tv_day).setTag(value);
        boolean hasDiary = hasDiaryInThisDay(value);
        if (helper.getAdapterPosition() % Calendar.DAY_OF_WEEK == 0) {
            helper.setTextColor(R.id.tv_day, context.getResources().getColor(hasDiary ? R.color.colorAccent :R.color.colorAccentLight));
        } else if ((helper.getAdapterPosition() + 1) % Calendar.DAY_OF_WEEK == 0) {
            helper.setTextColor(R.id.tv_day, context.getResources().getColor(hasDiary ? R.color.colorPrimary : R.color.colorPrimaryLight));
        } else {
            helper.setTextColor(R.id.tv_day, context.getResources().getColor(hasDiary ? R.color.color_black : R.color.color_black_light));
        }
        TextView tv = helper.getView(R.id.tv_day);
        Drawable bottomDrawable = context.getResources().getDrawable(R.drawable.black_vertical_line);
        bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
        boolean isToday = (year == currYear && month == currMonth && value > 0 && value == currDay);
        tv.setCompoundDrawables(null, null, null, isToday ? bottomDrawable : null);
        helper.addOnClickListener(R.id.tv_day);
    }

    public void notifyDayDiary(int year,int month) {
        this.year = year;
        this.month = month;
        diaryByMonth = DbManager.getInstance().getDiaryByMonth(year, month);
        notifyDataSetChanged();
    }

    private boolean hasDiaryInThisDay(int day) {
        if(diaryByMonth == null || day <= 0) return false;
        for (DiaryInfo info : diaryByMonth) {
            if(info.getDay() == day) {
                return true;
            }
        }
        return false;
    }
}
