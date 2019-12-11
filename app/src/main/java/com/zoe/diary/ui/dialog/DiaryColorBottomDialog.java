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

import androidx.annotation.NonNull;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.zoe.diary.R;
import com.zoe.diary.ui.adapter.DiaryColorAdapter;
import com.zoe.diary.ui.widget.GlideEngine;
import com.zoe.diary.utils.DisplayUtil;
import com.zoe.diary.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaryColorBottomDialog extends Dialog implements AdapterView.OnItemClickListener {

    private static final String[] colorArr = new String[] {
       "#d81e06","#d6204b","#e0620d","#ea9518","#f6ef37","#0e932e",
       "#112079","#0061b2","#17abe3","#13227a","#82529d","#db639b",
       "#83c6c2","#aad08f","#dbdbdb","#bfbfbf","#8a8a8a","#515151",
       "#2c2c2c"
    };

    @BindView(R.id.grid_color)
    GridView gridColor;

    private Context context;
    private OnColorSelectedListener colorSelectedListener;
    private int w;
    private int h;

    public DiaryColorBottomDialog(@NonNull Context context, int w, int h) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.w = w;
        this.h = h;
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
        gridColor.setOnItemClickListener(this);
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

    @OnClick(R.id.rl_select_img)
    public void selectPic() {
        dismiss();
        PictureSelector.create((Activity) context)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .imageSpanCount(4)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .enableCrop(true)
                .withAspectRatio(w, h)
                .rotateEnabled(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(colorSelectedListener != null) {
            colorSelectedListener.colorSelect(position, colorArr[position]);
        }
        dismiss();
    }

    public void setOnColorSelectedListener(OnColorSelectedListener listener) {
        this.colorSelectedListener = listener;
    }

    public interface OnColorSelectedListener {
        void colorSelect(int pos, String color);
    }
}
