package com.zoe.diary.net.request.diary;

import com.zoe.diary.net.base.BasePresenter;
import com.zoe.diary.net.base.RxObserver;
import com.zoe.diary.net.response.DiaryListResponse;

/**
 * author zoe
 * created 2019/6/19 10:18
 */

public class DiaryPresenter extends BasePresenter<DiaryContract.IView> implements DiaryContract.IPresenter {

    private DiaryModel mModel = new DiaryModel();

    @Override
    public void getAllDiary() {
        RxObserver<DiaryListResponse> observer = new RxObserver<DiaryListResponse>() {
            @Override
            protected void onSuccess(DiaryListResponse response) {
                getView().onAllDiarySuccess(response);
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                getView().onAllDiaryFailed();
            }
        };
        addDisposable(observer);
        mModel.getAllDiary().subscribe(observer);
    }
}
