package com.zoe.diary.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zoe.diary.database.domain.DiaryColor;

/**
 * author zoe
 * created 2019/12/11 10:12
 */
@Dao
public interface DiaryColorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(DiaryColor diaryColor);

    @Query("SELECT * FROM diary_color where year =:year and month =:month")
    DiaryColor getDiaryColorByDate(int year, int month);

}
