package com.zoe.diary.net.response.info;

/**
 * author zoe
 * created 2019/12/5 14:39
 */

public class DiaryInfo {

    public int id; //diary主键id
    public String title; //diary标题
    public String content;//diary内容
    public int weather; //diary天气
    public int mood; //心情
    public String location;//定位
    public String date; //日期
    public String tag; //标签
    public String img; //图片

    @Override
    public String toString() {
        return "DiaryInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", weather=" + weather +
                ", mood=" + mood +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", tag='" + tag + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
