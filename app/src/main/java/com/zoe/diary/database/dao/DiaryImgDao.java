package com.zoe.diary.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zoe.diary.database.domain.ImgInfo;

import java.util.List;

/**
 * author zoe
 * created 2019/12/10 14:41
 */

@Dao
public interface DiaryImgDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ImgInfo img);

    @Query("SELECT * FROM diary_img where diary_id = :diaryId")
    List<ImgInfo> getAllImg(int diaryId);

}
