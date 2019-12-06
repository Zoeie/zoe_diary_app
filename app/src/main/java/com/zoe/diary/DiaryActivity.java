package com.zoe.diary;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zoe.diary.net.request.diary.DiaryContract;
import com.zoe.diary.net.request.diary.DiaryPresenter;
import com.zoe.diary.net.response.DiaryResponse;
import com.zoe.diary.ui.activity.base.BaseMVPActivity;
import com.zoe.diary.ui.fragment.DiaryFragment;

import butterknife.BindView;

public class DiaryActivity extends BaseMVPActivity<DiaryPresenter, DiaryContract.IView> implements DiaryContract.IView {

    @BindView(R.id.bnv_main)
    BottomNavigationView navigationView;
    public Fragment[] fragmentList;
    private int lastFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (lastFragment != 0) {
                        switchFragment(lastFragment, 0);
                        lastFragment = 0;
                    }
                    return true;
                case R.id.navigation_buy:
                    if (lastFragment != 1) {
                        switchFragment(lastFragment, 1);
                        lastFragment = 1;
                    }
                    return true;
                case R.id.navigation_user:
                    if (lastFragment != 2) {
                        switchFragment(lastFragment, 2);
                        lastFragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected DiaryPresenter createPresenter() {
        return new DiaryPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        Fragment headFragment = DiaryFragment.getInstance("Fragment 一");
        Fragment orderFragment = DiaryFragment.getInstance("Fragment 二");
        Fragment userFragment = DiaryFragment.getInstance("Fragment 三");
        fragmentList = new Fragment[]{headFragment, orderFragment, userFragment};
        lastFragment = 0;
        //为navigationView设置点击事件
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //设置默认页面为headFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, headFragment).show(headFragment).commit();
        navigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void initData() {
        mPresenter.getAllDiary();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onAllDiarySuccess(DiaryResponse response) {

    }

    @Override
    public void onAllDiaryFailed() {

    }

    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragmentList[lastFragment]);
        //判断transaction中是否加载过index对应的页面，若没加载过则加载
        if (!fragmentList[index].isAdded()) {
            transaction.add(R.id.fl_main, fragmentList[index]);
        }
        //根据角标将fragment显示出来
        transaction.show(fragmentList[index]).commitAllowingStateLoss();
    }
}
