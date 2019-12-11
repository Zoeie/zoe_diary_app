package com.zoe.diary.database.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * author zoe
 * created 2019/12/11 10:09
 */

@Entity(tableName = "diary_color")
public class DiaryColor {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    public int id;
    public int year;
    public int month;
    @ColumnInfo(name = "color_type")
    public int colorType;
    @ColumnInfo(name = "img_path")
    public String imgPath;
    public String color;

    public DiaryColor(int year, int month, int colorType, String imgPath,String color) {
        this.year = year;
        this.month = month;
        this.colorType = colorType;
        this.imgPath = imgPath;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "DiaryColor{" +
                "id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", colorType=" + colorType +
                ", imgPath='" + imgPath + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
