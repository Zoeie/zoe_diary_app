package com.zoe.diary.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zoe.diary.R;
import com.zoe.diary.ui.activity.DiaryEditActivity;
import com.zoe.diary.ui.adapter.DiaryColorAdapter;
import com.zoe.diary.ui.adapter.DiaryIconAdapter;
import com.zoe.diary.utils.DisplayUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryColorBottomDialog extends Dialog {

    private static final String[] colorArr = new String[] {
       "#d81e06","#d6204b","#e0620d","#ea9518","#f6ef37","#0e932e",
       "#112079","#0061b2","#17abe3","#13227a","#82529d","#db639b",
       "#83c6c2","#aad08f","#dbdbdb","#bfbfbf","#8a8a8a","#515151",
       "#2c2c2c"
    };

    @BindView(R.id.grid_color)
    GridView gridColor;

    @BindView(R.id.tv_select_pic)
    TextView tvSelectPic;

    private Context context;

    public DiaryColorBottomDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_color_bg, null);
        setContentView(view);//这行一定要写在前面
        setDialogAttr();
        ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        gridColor.setAdapter(new DiaryColorAdapter(context, colorArr));
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
}
