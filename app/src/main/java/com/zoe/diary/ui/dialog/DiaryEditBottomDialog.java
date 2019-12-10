package com.zoe.diary.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.ui.activity.DiaryEditActivity;
import com.zoe.diary.ui.adapter.DiaryIconAdapter;
import com.zoe.diary.utils.DisplayUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryEditBottomDialog extends Dialog implements AdapterView.OnItemClickListener {

    @BindView(R.id.grid_mood)
    GridView gridMood;

    @BindView(R.id.grid_weather)
    GridView gridWeather;

    private Context context;
    private int weather;
    private int mood;

    public DiaryEditBottomDialog(Context context,int weather,int mood) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.weather = weather;
        this.mood = mood;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_edit, null);
        setContentView(view);//这行一定要写在前面
        setDialogAttr();
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        gridMood.setAdapter(new DiaryIconAdapter(context, DiaryEditActivity.moodArr , mood));
        gridWeather.setAdapter(new DiaryIconAdapter(context, DiaryEditActivity.weatherArr, weather));
        gridMood.setOnItemClickListener(this);
        gridWeather.setOnItemClickListener(this);
    }

    private void setDialogAttr() {
        setCancelable(true);//是否可以取消，即点击返回键是否可以取消
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        if (window == null) return;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (DisplayUtil.getScreenWidth((Activity) context) * (0.9));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.DIALOG_ANIM);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int type = Constants.TYPE.MOOD;
        if (parent.getId() == R.id.grid_mood) {
            type = Constants.TYPE.MOOD;
        } else if (parent.getId() == R.id.grid_weather) {
            type = Constants.TYPE.WEATHER;
        }
        if (onDialogItemClickListener != null) {
            onDialogItemClickListener.onItemClick(type, position);
        }
        dismiss();
    }

    private OnDialogItemClickListener onDialogItemClickListener;

    public void setOnDialogItemClickListener(OnDialogItemClickListener listener) {
        onDialogItemClickListener = listener;
    }

    public interface OnDialogItemClickListener {
        void onItemClick(int type, int pos); //点击的类型，点击的位置
    }
}
