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

/**
 * author zoe
 * created 2019/12/5 17:30
 */

public class DiaryFragment extends BaseFragment {

    @BindView(R.id.tv_text)
    TextView tvText;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private static final String KEY_TAG = "KEY_TAG";
    private String tag = "";
    private List<Fragment> fragmentList;
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
        tvText.setText(tag);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(false, new DiaryPageTranform());
        DiaryAdapter diaryAdapter = new DiaryAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(diaryAdapter);
    }
}
