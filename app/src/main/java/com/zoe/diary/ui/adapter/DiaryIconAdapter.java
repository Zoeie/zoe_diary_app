package com.zoe.diary.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zoe.diary.R;

public class DiaryIconAdapter extends BaseAdapter {

    private Context context;
    private int[] res;
    private int targetPos;

    public DiaryIconAdapter(Context context, int[] res,int targetPos) {
        this.context = context;
        this.res = res;
        this.targetPos = targetPos;
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
        ImageView iv = (ImageView) View.inflate(context, R.layout.diary_edit_img, null);
        iv.setImageResource(res[position]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageTintList(ColorStateList.valueOf(context.getResources()
                    .getColor(targetPos == position ? R.color.colorPrimary:R.color.color_B3000000)));
        }
        return iv;
    }
}
