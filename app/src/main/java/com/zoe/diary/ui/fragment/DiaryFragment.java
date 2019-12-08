package com.zoe.diary.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.zoe.diary.R;
import com.zoe.diary.ui.adapter.DiaryAdapter;
import com.zoe.diary.ui.fragment.base.BaseFragment;
import com.zoe.diary.ui.transform.DiaryPageTranform;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author zoe
 * created 2019/12/5 17:30
 */

public class DiaryFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private boolean showCalendar = true;
    private static final String KEY_TAG = "KEY_TAG";
    private String tag = "";
    private List<CalendarFragment> fragmentList;
    private int targetYear = Calendar.getInstance().get(Calendar.YEAR);

    public static DiaryFragment getInstance(String tag) {
        LogUtil.d("tag:" + tag);
        DiaryFragment diaryFragment = new DiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TAG, tag);
        diaryFragment.setArguments(bundle);
        return diaryFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_diary;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            tag = arguments.getString(KEY_TAG);
        }
        initCalendarFragment();
    }

    private void initCalendarFragment() {
        fragmentList = new ArrayList<>();
        for (int month = 0; month < 12; month++) {
            fragmentList.add(CalendarFragment.getInstance(targetYear, month));
        }
    }

    private void initView() {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(false, new DiaryPageTranform());
        DiaryAdapter diaryAdapter = new DiaryAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(diaryAdapter);
        viewPager.setCurrentItem(Calendar.getInstance().get(Calendar.MONTH));
        viewPager.addOnPageChangeListener(this);
    }

    @OnClick(R.id.tv_rotate)
    public synchronized void onRotate() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem == fragmentList.size() - 1) {
            executeRotate(currentItem - 1);
            executeRotate(currentItem);
        } else if (currentItem == 0) {
            executeRotate(currentItem);
            executeRotate(currentItem + 1);
        } else {
            executeRotate(currentItem - 1);
            executeRotate(currentItem);
            executeRotate(currentItem + 1);
        }
        showCalendar = !showCalendar;
    }

    private void executeRotate(int pos) {
        CalendarFragment target = fragmentList.get(pos);
        target.doAnim();
    }

    private void notifyDiaryViewType() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem == fragmentList.size() - 1) {
            executeUpdateViewType(currentItem - 1);
            executeUpdateViewType(currentItem);
        } else if (currentItem == 0) {
            executeUpdateViewType(currentItem);
            executeUpdateViewType(currentItem + 1);
        } else {
            executeUpdateViewType(currentItem - 1);
            executeUpdateViewType(currentItem);
            executeUpdateViewType(currentItem + 1);
        }
    }

    protected void executeUpdateViewType(int pos) {
        CalendarFragment target = fragmentList.get(pos);
        target.setShowCalendar(showCalendar);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        notifyDiaryViewType();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
