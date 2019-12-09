package com.zoe.diary.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;

import com.zoe.diary.R;
import com.zoe.diary.ui.adapter.YearAdapter;
import com.zoe.diary.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author zoe
 * created 2019/12/9 13:48
 */

public class DiaryDateBottomDialog extends Dialog implements YearAdapter.OnDateListener {

    @BindView(R.id.rv_date_year)
    RecyclerView rvDateYear;

    private Context context;
    private OnDateListener onDateListener;
    private int targetYear;
    private int targetMonth;
    private static final int START_YEAR = 2010;
    private static final int END_YEAR = 2023;

    public DiaryDateBottomDialog(Context context, int targetYear,int targetMonth) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.targetYear = targetYear;
        this.targetMonth = targetMonth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_date, null);
        setContentView(view);//这行一定要写在前面
        setDialogAttr();
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        YearAdapter yearAdapter = new YearAdapter(genYearList(), context, targetYear, targetMonth);
        yearAdapter.setOnDateListener(this);
        rvDateYear.setAdapter(yearAdapter);
    }

    //从2010 - 2023年
    private List<Integer> genYearList() {
        List<Integer> list = new ArrayList<>();
        for (int i = START_YEAR; i <= END_YEAR; i++) {
            list.add(i);
        }
        return list;
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
    public void onDate(int year, int month) {
        if (onDateListener != null) {
            onDateListener.onDate(year, month);
            dismiss();
        }
    }

    public void setOnDateListener(OnDateListener onDateListener) {
        this.onDateListener = onDateListener;
    }

    public interface OnDateListener {
        void onDate(int year, int month);
    }

    @Override
    public void show() {
        super.show();
        rvDateYear.scrollToPosition(targetYear - START_YEAR);
    }
}
