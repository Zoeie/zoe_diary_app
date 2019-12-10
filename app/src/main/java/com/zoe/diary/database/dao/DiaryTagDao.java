package com.zoe.diary.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zoe.diary.database.domain.TagInfo;

import java.util.List;

/**
 * author zoe
 * created 2019/12/10 14:32
 */

@Dao
public interface DiaryTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TagInfo tag);

    @Query("SELECT * FROM diary_tag where diary_id = :diaryId")
    List<TagInfo> getAllTag(int diaryId);

}
