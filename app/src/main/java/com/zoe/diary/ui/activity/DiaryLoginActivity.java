package com.zoe.diary.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zoe.diary.R;
import com.zoe.diary.entity.ThirdPart;
import com.zoe.diary.ui.activity.base.BaseActivity;
import com.zoe.diary.ui.fragment.BindAccountFragment;
import com.zoe.diary.ui.fragment.LoginFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author zoe
 * created 2019/12/12 9:33
 */

public class DiaryLoginActivity extends BaseActivity implements LoginFragment.OnGoToBindAccountListener /*BaseMVPActivity<LoginPresenter, LoginContract.IView> implements LoginContract.IView */ {

    private Fragment[] fragmentList;
    private int lastFragment;

    @BindView(R.id.tv_page_title)
    TextView tvPageTitle;
    private BindAccountFragment accountFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        LoginFragment loginFragment = LoginFragment.getInstance();
        loginFragment.setOnGoToBindAccountListener(this);
        accountFragment = BindAccountFragment.getInstance();
        fragmentList = new Fragment[]{loginFragment, accountFragment};
        lastFragment = 0;
        //设置默认页面为headFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, loginFragment).show(loginFragment).commit();
    }

    private void switchIndex(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragmentList[lastFragment]);
        //判断transaction中是否加载过index对应的页面，若没加载过则加载
        if (!fragmentList[index].isAdded()) {
            transaction.add(R.id.fl_container, fragmentList[index]);
        }
        //根据角标将fragment显示出来
        transaction.show(fragmentList[index]).commitAllowingStateLoss();
        lastFragment = index;
        tvPageTitle.setText(index == 0 ? "登录" : "绑定邮箱");
    }

    private void initData() {
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_login;
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        if (lastFragment == 1) {
            switchIndex(0);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (lastFragment == 1) {
            switchIndex(0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void gotoBindAccount(ThirdPart part) {
        accountFragment.setPart(part);
        //调整到绑定账号的界面
        switchIndex(1);
    }
}
