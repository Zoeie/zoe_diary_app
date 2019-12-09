package com.zoe.diary.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.system.Os;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoe.diary.R;
import com.zoe.diary.utils.DateUtil;
import com.zoe.diary.utils.LogUtil;

public class DiaryYearAdapter extends BaseAdapter {

    private Context context;
    private int[] res;
    private int month;
    private boolean isTargetYear;

    public DiaryYearAdapter(Context context, int[] res, int month, boolean isTargetYear) {
        this.context = context;
        this.res = res;
        this.month = month;
        this.isTargetYear = isTargetYear;
    }

    @Override
    public int getCount() {
        return res.length;
    }

    @Override
    public Object getItem(int position) {
        return res[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView) View.inflate(context, R.layout.diary_month_label, null);
        tv.setText(DateUtil.convertNumberToEnDesc(res[position]));
        boolean isSelected = (isTargetYear && position == month);
        tv.setTextColor(context.getResources().getColor(isSelected ? R.color.colorPrimary : R.color.color_black));
        tv.setBackgroundResource(isSelected ? R.drawable.dialog_month_text_selected_bg : 0);
        return tv;
    }
}
