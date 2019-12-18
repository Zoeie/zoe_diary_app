package com.zoe.diary.entity;

import java.io.Serializable;

/**
 * author zoe
 * created 2019/12/18 16:49
 */

public class ThirdPart implements Serializable {
    private String userId;
    private String userIcon;
    private String userName;
    private int type;

    public ThirdPart(String userId, String userIcon, String userName) {
        this.userId = userId;
        this.userIcon = userIcon;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
