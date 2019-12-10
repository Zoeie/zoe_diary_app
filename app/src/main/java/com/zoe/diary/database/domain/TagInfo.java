package com.zoe.diary.database.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * author zoe
 * created 2019/12/10 14:33
 */
@Entity(tableName = "diary_tag")
public class TagInfo {

    public TagInfo() {}

    public TagInfo(long diaryId, String tag) {
        this.diaryId = diaryId;
        this.tag = tag;
    }

    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;

    @ColumnInfo(name = "diary_id")
    public long diaryId;

    public String tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(long diaryId) {
        this.diaryId = diaryId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
