package com.zoe.diary.net.request.save;

import com.zoe.diary.net.contract.IContract;
import com.zoe.diary.net.response.DiaryListResponse;
import com.zoe.diary.net.response.DiarySaveResponse;

import java.util.Date;
import java.util.List;

/**
 * author zoe
 * created 2019/6/20 9:22
 */

public interface SaveContract {

    interface IPresenter extends IContract.IPresenter<SaveContract.IView> {
        void saveDiary(List<String> imgPaths, String title, String content,
                       int weather, int mood, String location, Date date, List<String> tags);
    }

    interface IView extends IContract.IView {
        void onSaveDiarySuccess(DiarySaveResponse response);
        void onSaveDiaryFailed();
    }
}
