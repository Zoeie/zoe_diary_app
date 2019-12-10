package com.zoe.diary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zoe.diary.R;
import com.zoe.diary.database.DbManager;
import com.zoe.diary.database.db.AppDatabase;
import com.zoe.diary.database.domain.DiaryInfo;
import com.zoe.diary.ui.activity.base.BaseActivity;
import com.zoe.diary.ui.adapter.DiaryMonthListAdapter;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * author zoe
 * created 2019/12/10 15:37
 */

public class DiaryMonthActivity extends BaseActivity {

    @BindView(R.id.rv_month_list)
    RecyclerView rvMonthList;

    @BindView(R.id.tv_page_title)
    TextView tvPageTitle;

    @BindView(R.id.tv_no_diary_tip)
    TextView tvNoDiaryTip;

    public static final String KEY_YEAR = "YEAR";
    public static final String KEY_MONTH = "MONTH";

    private static final int REQUEST_CODE = 1000;
    private int year;
    private int month;

    private List<DiaryInfo> diaryByMonth = new ArrayList<>();
    private DiaryMonthListAdapter diaryMonthListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        year = intent.getIntExtra(KEY_YEAR, Calendar.getInstance().get(Calendar.YEAR));
        month = intent.getIntExtra(KEY_MONTH, Calendar.getInstance().get(Calendar.MONTH));
        List<DiaryInfo> diaryByMonth = DbManager.getInstance().getDiaryByMonth(year, month);
        this.diaryByMonth.addAll(diaryByMonth);
    }

    private void initView() {
        for (DiaryInfo info : diaryByMonth) {
            LogUtil.d(info.toString());
        }
        tvPageTitle.setText(String.format(Locale.getDefault(), "%d月/%d", month + 1, year));
        diaryMonthListAdapter = new DiaryMonthListAdapter(this, diaryByMonth);
        diaryMonthListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转到编辑页面
                Intent intent = new Intent(DiaryMonthActivity.this, DiaryEditActivity.class);
                intent.putExtra(DiaryEditActivity.KEY_ID, diaryByMonth.get(position).getId());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        rvMonthList.setAdapter(diaryMonthListAdapter);
        updateDiaryTip();
    }

    private void notifyDataChanged() {
        diaryByMonth.clear();
        List<DiaryInfo> diaryByMonth = DbManager.getInstance().getDiaryByMonth(year, month);
        for (DiaryInfo info : diaryByMonth) {
            LogUtil.d(info.toString());
        }
        this.diaryByMonth.addAll(diaryByMonth);
        diaryMonthListAdapter.notifyDataSetChanged();
    }

    private void updateDiaryTip() {
        tvNoDiaryTip.setVisibility(diaryByMonth.size() <= 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_month;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            notifyDataChanged();
        }
    }
}
