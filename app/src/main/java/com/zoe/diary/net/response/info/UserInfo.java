package com.zoe.diary.net.response.info;

import java.io.Serializable;

public class UserInfo extends BaseInfo implements Serializable {
    public String headPortrait;
    public String userName;
    public String nickName;
    public boolean bindQQ;
    public boolean bindWeChat;
    public String userId;

    @Override
    public String toString() {
        return "UserInfo{" +
                "headPortrait='" + headPortrait + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", bindQQ=" + bindQQ +
                ", bindWeChat=" + bindWeChat +
                ", userId='" + userId + '\'' +
                '}';
    }
}
