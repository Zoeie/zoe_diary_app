package com.zoe.diary.net.request.save;

import android.util.Log;

import com.zoe.diary.net.base.BasePresenter;
import com.zoe.diary.net.base.RxObserver;
import com.zoe.diary.net.request.diary.DiaryContract;
import com.zoe.diary.net.request.diary.DiaryModel;
import com.zoe.diary.net.response.DiaryListResponse;
import com.zoe.diary.net.response.DiarySaveResponse;

import java.util.Date;
import java.util.List;

/**
 * author zoe
 * created 2019/6/19 10:18
 */

public class SavePresenter extends BasePresenter<SaveContract.IView> implements SaveContract.IPresenter {

    private SaveModel mModel = new SaveModel();

    @Override
    public void saveDiary(List<String> imgPaths, String title, String content,
                          int weather, int mood, String location, Date date, List<String> tags) {
        RxObserver<DiarySaveResponse> observer = new RxObserver<DiarySaveResponse>() {
            @Override
            protected void onSuccess(DiarySaveResponse response) {
                getView().onSaveDiarySuccess(response);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                Log.e("xxx","errorCode:"+errorCode+",errorMsg:"+errorMsg);
                getView().onSaveDiaryFailed();
            }
        };
        addDisposable(observer);
        mModel.saveDiary(imgPaths,title,content,weather,mood,location,date,tags)
                .subscribe(observer);
    }
}
