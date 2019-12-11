package com.zoe.diary.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zoe.diary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author zoe
 * created 2019/12/11 17:45
 */

public class UserSettingItem extends RelativeLayout {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    @BindView(R.id.iv_sub_icon)
    ImageView ivSubIcon;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_sub_title)
    TextView tvSubTitle;

    @BindView(R.id.tv_message)
    TextView tvMessage;

    private Context context;
    private int iconResId;
    private int subIconResId;
    private String title;
    private String subTitle;
    private String message;

    public UserSettingItem(Context context) {
        super(context);
        init(context, null);
    }

    public UserSettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UserSettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UserSettingItem);
        iconResId = a.getResourceId(R.styleable.UserSettingItem_icon, R.mipmap.ic_launcher);
        title = a.getString(R.styleable.UserSettingItem_title);
        subIconResId = a.getResourceId(R.styleable.UserSettingItem_sub_icon, 0);
        subTitle = a.getString(R.styleable.UserSettingItem_sub_title);
        message = a.getString(R.styleable.UserSettingItem_message);
        a.recycle();
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.user_setting_item, this, true);
        ButterKnife.bind(this, this);
        setUI();
    }

    private void setUI() {
        ivIcon.setImageResource(iconResId);
        if(subIconResId != 0) {
            ivSubIcon.setImageResource(subIconResId);
        }
        tvTitle.setText(title);
        tvSubTitle.setText(subTitle);
        tvMessage.setText(message);
    }
}
