package com.zoe.diary.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zoe.diary.R;
import com.zoe.diary.utils.DisplayUtil;
import com.zoe.diary.utils.LogUtil;

public class DiaryColorAdapter extends BaseAdapter {

    private Context context;
    private String[] res;

    public DiaryColorAdapter(Context context, String[] res) {
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
        View view = View.inflate(context, R.layout.diary_color, null);
        TextView tvColor = view.findViewById(R.id.tv_color);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor(res[position]));
        drawable.setCornerRadius(DisplayUtil.dip2px(context, 20));
        tvColor.setBackground(drawable);
        return view;
    }
}
