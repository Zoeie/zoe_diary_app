package com.zoe.diary.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.ui.activity.base.BaseActivity;
import com.zoe.diary.utils.LogUtil;

import java.util.HashMap;
import java.util.Set;

import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * author zoe
 * created 2019/12/12 9:33
 */

public class DiaryLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_login;
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        finish();
    }

    @OnClick(R.id.tv_qq)
    public void onQQClick() {
        loginWithThirdPart(QQ.NAME);
    }

    @OnClick(R.id.tv_we_chat)
    public void onWeChatClick() {
        loginWithThirdPart(Wechat.NAME);
    }

    private void loginWithThirdPart(String name) {
        Platform plat = ShareSDK.getPlatform(name);
        //移除授权状态和本地缓存，下次授权会重新授权
        plat.removeAccount(true);
        //SSO授权，传false默认是客户端授权
        plat.SSOSetting(false);
        //授权回调监听，监听oncomplete，onerror，oncancel三种状态
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                LogUtil.d("onComplete i = " + i);
                Set<String> strings = hashMap.keySet();
                for (String key : strings) {
                    Object o = hashMap.get(key);
                    LogUtil.e("key:"+key);
                    LogUtil.d("o:"+o);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                throwable.printStackTrace();
                LogUtil.d("onError i = " + i);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.d("onCancel i = " + i);
            }
        });
        //抖音登录适配安卓9.0
        ShareSDK.setActivity(this);
        //要数据不要功能，主要体现在不会重复出现授权界面
        plat.showUser(null);
    }

}
