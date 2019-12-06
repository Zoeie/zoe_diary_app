package com.zoe.diary.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * author zoe
 * created 2019/12/6 16:47
 */

public class DiaryAdapter extends FragmentStatePagerAdapter {

    private static final int MONTH_NUM_IN_ONE_YEAR = 12; //1年12个月
    private List<Fragment> fragmentList;

    public DiaryAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return MONTH_NUM_IN_ONE_YEAR;
    }
}
