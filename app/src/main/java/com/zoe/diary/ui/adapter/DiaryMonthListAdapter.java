package com.zoe.diary.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoe.diary.R;
import com.zoe.diary.database.domain.DiaryInfo;
import com.zoe.diary.ui.activity.DiaryEditActivity;
import com.zoe.diary.utils.DateUtil;

import java.util.Calendar;
import java.util.List;

/**
 * author zoe
 * created 2019/12/10 15:43
 */

public class DiaryMonthListAdapter extends BaseQuickAdapter<DiaryInfo, BaseViewHolder> {

    private Context context;

    public DiaryMonthListAdapter(Context context, @Nullable List<DiaryInfo> data) {
        super(R.layout.diary_month_item, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiaryInfo item) {
        helper.setText(R.id.tv_day_en, DateUtil.convertEnToWeek(item.getDayOfWeek()));
        helper.setTextColor(R.id.tv_day_en, context.getResources().getColor(
                (item.getDayOfWeek() == Calendar.SUNDAY) ? R.color.colorAccent :
                        (item.getDayOfWeek() == Calendar.SATURDAY ? R.color.colorPrimary : R.color.color_black)
        ));
        helper.setText(R.id.tv_day_num,String.valueOf(item.day));
        helper.setTextColor(R.id.tv_day_num, context.getResources().getColor(
                (item.getDayOfWeek() == Calendar.SUNDAY) ? R.color.colorAccent :
                        (item.getDayOfWeek() == Calendar.SATURDAY ? R.color.colorPrimary : R.color.color_black)
        ));
        helper.setText(R.id.tv_content,String.valueOf(item.content));
        helper.setText(R.id.tv_title,String.valueOf(item.title));
        helper.setText(R.id.tv_title_other,String.valueOf(item.title));
        helper.setImageResource(R.id.iv_weather, DiaryEditActivity.weatherArr[item.weather]);
        RelativeLayout rlContentArea = helper.getView(R.id.rl_content_area);
        ImageView ivImg = helper.getView(R.id.iv_img);
        List<String> img = item.getImg();
        helper.setVisible(R.id.tv_title, (!TextUtils.isEmpty(item.title) && (img != null && img.size() > 0)));
        if (img != null && img.size() > 0) {
            rlContentArea.setVisibility(View.GONE);
            Glide.with(context).load(img.get(0)).centerCrop().into(ivImg);
        } else {
            ivImg.setImageResource(android.R.color.transparent);
            rlContentArea.setVisibility(View.VISIBLE);
        }
    }
}
