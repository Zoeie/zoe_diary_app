package com.zoe.diary.database;

import androidx.room.Transaction;

import com.zoe.diary.database.db.AppDatabase;
import com.zoe.diary.database.domain.DiaryInfo;
import com.zoe.diary.database.domain.ImgInfo;
import com.zoe.diary.database.domain.TagInfo;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author zoe
 * created 2019/12/10 13:42
 */

public class DbManager {

    private DbManager() {
    }

    public static DbManager getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final DbManager instance = new DbManager();
    }

    @Transaction
    public void insertDiaryInfo(DiaryInfo info) {
        long diaryId = AppDatabase.getDatabase().getDiaryContentDao().insert(info);
        LogUtil.d("diaryId:" + diaryId);
        for (String tag : info.getTag()) {
            TagInfo tagInfo = new TagInfo(diaryId, tag);
            AppDatabase.getDatabase().getDiaryTagDao().insert(tagInfo);
        }
        for (String img : info.getImg()) {
            ImgInfo imgInfo = new ImgInfo(diaryId, img);
            AppDatabase.getDatabase().getDiaryImgDao().insert(imgInfo);
        }
    }

    @Transaction
    public List<DiaryInfo> getDiaryContent() {
        List<DiaryInfo> diaryContent = AppDatabase.getDatabase().getDiaryContentDao().getDiaryContent();
        for (DiaryInfo diaryInfo : diaryContent) {
            List<TagInfo> tagInfoList = AppDatabase.getDatabase().getDiaryTagDao().getAllTag(diaryInfo.getId());
            List<String> tagList = new ArrayList<>();
            for (TagInfo tagInfo : tagInfoList) {
                tagList.add(tagInfo.getTag());
            }
            diaryInfo.setTag(tagList);
            List<ImgInfo> imgInfoList = AppDatabase.getDatabase().getDiaryImgDao().getAllImg(diaryInfo.getId());
            List<String> imgList = new ArrayList<>();
            for (ImgInfo imgInfo : imgInfoList) {
                imgList.add(imgInfo.getImgPath());
            }
            diaryInfo.setImg(imgList);
        }
        return diaryContent;
    }

}
