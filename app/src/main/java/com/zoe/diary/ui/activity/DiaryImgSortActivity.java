package com.zoe.diary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zoe.diary.R;
import com.zoe.diary.listener.StartDragListener;
import com.zoe.diary.ui.activity.base.BaseActivity;
import com.zoe.diary.ui.adapter.ImgSortAdapter;
import com.zoe.diary.ui.widget.MyItemTouchHelperCallback;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author zoe
 * created 2019/12/11 14:11
 */

public class DiaryImgSortActivity extends BaseActivity implements StartDragListener {

    public static final String KEY_IMG_LIST = "IMG_LIST";

    @BindView(R.id.rv_img_list)
    RecyclerView rvImgList;

    private List<String> imgList = new ArrayList<>();
    private ItemTouchHelper itemTouchHelper;
    private ImgSortAdapter imgSortAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        ArrayList<String> extra = intent.getStringArrayListExtra(KEY_IMG_LIST);
        imgList.addAll(extra);
    }

    private void initView() {
        imgSortAdapter = new ImgSortAdapter(imgList, this, this);
        imgSortAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.diary_img) {
                    //TODO 预览裁剪等编辑工作
                }
            }
        });
        rvImgList.setAdapter(imgSortAdapter);
        //条目触摸帮助类
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(imgSortAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rvImgList);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_img_sort;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        setFinishData();
        finish();
    }

    @Override
    public void onBackPressed() {
        setFinishData();
        super.onBackPressed();
    }

    private void setFinishData() {
        LogUtil.d("finish data:" + imgSortAdapter.getData().size());
        Intent intent = new Intent();
        intent.putStringArrayListExtra(KEY_IMG_LIST, (ArrayList<String>) imgSortAdapter.getData());
        setResult(RESULT_OK, intent);
    }


}
