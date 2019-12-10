package com.zoe.diary.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zoe.diary.database.domain.DiaryInfo;

import java.util.List;

/**
 * author zoe
 * created 2019/12/10 13:37
 */

@Dao
public interface DiaryContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(DiaryInfo diaryInfo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(DiaryInfo diaryInfo);

    @Query("SELECT * FROM diary_content")
    List<DiaryInfo> getDiaryContent();

    @Query("SELECT * FROM diary_content where year =:year and month =:month")
    List<DiaryInfo> getDiaryByMonth(int year,int month);

    @Query("SELECT * FROM diary_content where id =:id")
    DiaryInfo getDiaryById(int id);
}
