package com.zoe.diary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.database.DbManager;
import com.zoe.diary.database.domain.DiaryColor;
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
import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatSupportable;

public class DiaryActivity extends BaseMVPActivity<DiaryPresenter, DiaryContract.IView> implements DiaryContract.IView , SkinCompatSupportable {

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        String path = selectList.get(0).getCutPath();
                        if (fragmentList[lastFragment] != null && fragmentList[lastFragment] instanceof DiaryFragment) {
                            DiaryFragment diaryFragment = (DiaryFragment) fragmentList[lastFragment];
                            int targetYear = diaryFragment.getTargetYear();
                            int targetMonth = diaryFragment.getTargetMonth();
                            DiaryColor diaryColorByDate = DbManager.getInstance().getDiaryColorByDate(targetYear, targetMonth);
                            if (diaryColorByDate == null) {
                                DiaryColor color = new DiaryColor(targetYear, targetMonth, Constants.COLOR_TYPE.COLOR_PIC, path, "#D81B60");
                                DbManager.getInstance().insertDiaryColor(color);
                            } else {
                                diaryColorByDate.setColorType(Constants.COLOR_TYPE.COLOR_PIC);
                                diaryColorByDate.setImgPath(path);
                                DbManager.getInstance().insertDiaryColor(diaryColorByDate);
                            }
                            diaryFragment.updateSolidBg();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void applySkin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(SkinCompatResources.getInstance().getColor(R.color.colorPrimary));
        }
    }
}
