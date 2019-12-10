package com.zoe.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zoe.diary.database.DbManager;
import com.zoe.diary.database.domain.DiaryInfo;
import com.zoe.diary.net.request.diary.DiaryContract;
import com.zoe.diary.net.request.diary.DiaryPresenter;
import com.zoe.diary.net.response.DiaryListResponse;
import com.zoe.diary.ui.activity.DiaryEditActivity;
import com.zoe.diary.ui.activity.base.BaseMVPActivity;
import com.zoe.diary.ui.fragment.DiaryFragment;
import com.zoe.diary.ui.fragment.MyFragment;
import com.zoe.diary.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
                    LogUtil.d("navigation_home lastFragment:" + lastFragment);
                    if (lastFragment != 0) {
                        switchFragment(lastFragment, 0);
                        lastFragment = 0;
                    }
                    return true;
                case R.id.navigation_user:
                    LogUtil.d("navigation_user lastFragment:" + lastFragment);
                    if (lastFragment != 1) {
                        switchFragment(lastFragment, 1);
                        lastFragment = 1;
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
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initView();
        initData();
    }

    private void initView() {
        Fragment diaryFragment = DiaryFragment.getInstance("Fragment Diary");
        Fragment myFragment = MyFragment.getInstance("Fragment My");
        fragmentList = new Fragment[]{diaryFragment, myFragment};
        lastFragment = 0;
        //为navigationView设置点击事件
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //设置默认页面为headFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, diaryFragment).show(diaryFragment).commit();
        navigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void initData() {
        //mPresenter.getAllDiary();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onAllDiarySuccess(DiaryListResponse response) {

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
        LogUtil.d("lastFragment:" + lastFragment + ",index:" + index);
        //根据角标将fragment显示出来
        transaction.show(fragmentList[index]).commitAllowingStateLoss();
    }

    @OnClick(R.id.fbtn_add_diary)
    public void onAddDiary() {
        startActivity(new Intent(DiaryActivity.this, DiaryEditActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<DiaryInfo> diaryContent = DbManager.getInstance().getDiaryContent();
        for (DiaryInfo info : diaryContent) {
            LogUtil.d(info.toString());
        }
    }
}
