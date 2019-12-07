package com.zoe.diary.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.zoe.diary.R;
import com.zoe.diary.ui.adapter.DiaryIconAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomDialogView extends Dialog implements AdapterView.OnItemClickListener {

    @BindView(R.id.grid_mood)
    GridView gridMood;

    @BindView(R.id.grid_weather)
    GridView gridWeather;

    private static final int[] moodArr = new int[] {
        R.mipmap.mood_smile,R.mipmap.mood_smile,R.mipmap.mood_smile,R.mipmap.mood_smile,
        R.mipmap.mood_smile,R.mipmap.mood_smile,R.mipmap.mood_smile,R.mipmap.mood_smile,
        R.mipmap.mood_smile,R.mipmap.mood_smile,R.mipmap.mood_smile,R.mipmap.mood_smile,
    };

    private static final int[] weatherArr = new int[] {
        R.mipmap.weather_cloudy, R.mipmap.weather_cloudy,R.mipmap.weather_cloudy,R.mipmap.weather_cloudy,
        R.mipmap.weather_cloudy, R.mipmap.weather_cloudy,R.mipmap.weather_cloudy,R.mipmap.weather_cloudy,
        R.mipmap.weather_cloudy, R.mipmap.weather_cloudy,R.mipmap.weather_cloudy,R.mipmap.weather_cloudy,
    };

    private Context context;

    public BottomDialogView(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
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
        gridMood.setAdapter(new DiaryIconAdapter(context,moodArr));
        gridWeather.setAdapter(new DiaryIconAdapter(context,weatherArr));
        gridMood.setOnItemClickListener(this);
        gridWeather.setOnItemClickListener(this);
    }

    private void setDialogAttr() {
        setCancelable(true);//是否可以取消，即点击返回键是否可以取消
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.grid_mood) {
            Toast.makeText(context,"表情："+ position,Toast.LENGTH_SHORT).show();
        } else if(parent.getId() == R.id.grid_weather) {
            Toast.makeText(context,"天气："+ position,Toast.LENGTH_SHORT).show();
        }
    }
}
