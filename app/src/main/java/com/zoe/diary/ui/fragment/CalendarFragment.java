package com.zoe.diary.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zoe.diary.R;
import com.zoe.diary.ui.adapter.DayAdapter;
import com.zoe.diary.ui.fragment.base.BaseFragment;
import com.zoe.diary.utils.DateUtil;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * author zoe
 * created 2019/12/6 16:43
 */

public class CalendarFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_date)
    RecyclerView rvDate;

    @BindView(R.id.tv_month_number)
    TextView tvMonthNumber;

    @BindView(R.id.tv_month_english)
    TextView tvMonthEnglish;

    private static final String KEY_YEAR = "YEAR";
    private static final String KEY_MONTH = "MONTH";
    private int year;
    private int month;

    public static CalendarFragment getInstance(int year, int month) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_YEAR, year);
        bundle.putInt(KEY_MONTH, month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_calendar;
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
            year = arguments.getInt(KEY_YEAR);
            month = arguments.getInt(KEY_MONTH);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int offsetDay = (dayOfWeek + 1) % Calendar.DAY_OF_WEEK;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        DayAdapter dayAdapter = new DayAdapter(getDayData(offsetDay, daysInMonth), getActivity());
        dayAdapter.setOnItemChildClickListener(this);
        rvDate.setAdapter(dayAdapter);
    }

    private List<Integer> getDayData(int offsetDay, int daysInMonth) {
        List<Integer> dayList = new ArrayList<>();
        int count = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < Calendar.DAY_OF_WEEK; j++) {
                int day;
                int realNumber = count - offsetDay;
                if (realNumber < 0) {
                    day = 0;
                } else if (daysInMonth - realNumber < 0) {
                    day = 0;
                } else {
                    day = realNumber;
                }
                dayList.add(day);
                count++;
            }
        }
        return dayList;
    }

    private void initView() {
        //月份从0开始的
        tvMonthNumber.setText(String.valueOf(month + 1));
        tvMonthEnglish.setText(DateUtil.convertNumberToEnDesc(month));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        String tag = (String) view.getTag();
        Toast.makeText(getActivity(), tag, Toast.LENGTH_SHORT).show();
    }
}
