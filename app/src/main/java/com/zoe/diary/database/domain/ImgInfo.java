package com.zoe.diary.database.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * author zoe
 * created 2019/12/10 14:42
 */

@Entity(tableName = "diary_img")
public class ImgInfo {

    public ImgInfo() {}

    public ImgInfo(long diaryId, String imgPath) {
        this.diaryId = diaryId;
        this.imgPath = imgPath;
    }

    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;

    @ColumnInfo(name = "diary_id")
    public long diaryId;

    public String imgPath;

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
