package com.zoe.diary.net.request.diary;

import com.zoe.diary.net.contract.IContract;
import com.zoe.diary.net.response.DiaryResponse;

/**
 * author zoe
 * created 2019/6/20 9:22
 */

public interface DiaryContract {

    interface IPresenter extends IContract.IPresenter<DiaryContract.IView> {
        void getAllDiary();
    }

    interface IView extends IContract.IView {
        void onAllDiarySuccess(DiaryResponse response);
        void onAllDiaryFailed();
    }
}
