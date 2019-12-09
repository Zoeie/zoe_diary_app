package com.zoe.diary.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoe.diary.R;
import com.zoe.diary.utils.DateUtil;

public class DiaryYearAdapter extends BaseAdapter {

    private Context context;
    private int[] res;

    public DiaryYearAdapter(Context context, int[] res) {
        this.context = context;
        this.res = res;
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
        return tv;
    }
}
