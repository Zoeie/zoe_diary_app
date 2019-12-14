package com.zoe.diary.utils;

import android.widget.Toast;

import androidx.annotation.IntegerRes;

import com.zoe.diary.BuildConfig;
import com.zoe.diary.app.DiaryApplication;

/**
 * 吐司工具类
 */

public class ToastUtils {

    /**
     * 调试模式下可显示
     *
     * @param msg
     */
    public static void showDebug(String msg) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(DiaryApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调试模式下可显示
     *
     * @param resId
     */
    public static void showDebug(@IntegerRes int resId) {
        if (BuildConfig.DEBUG) {
            final String text = ResUtils.getStringRes(resId);
            Toast.makeText(DiaryApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短暂显示
     *
     * @param msg
     */
    public static void showShort(CharSequence msg) {
        Toast.makeText(DiaryApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示
     *
     * @param resId
     */
    public static void showShort(int resId) {
        final String text = ResUtils.getStringRes(resId);
        Toast.makeText(DiaryApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     *
     * @param msg
     */
    public static void showLong(CharSequence msg) {
        Toast.makeText(DiaryApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 短暂显示
     *
     * @param resId
     */
    public static void showLong(int resId) {
        final String text = ResUtils.getStringRes(resId);
        Toast.makeText(DiaryApplication.getInstance(), text, Toast.LENGTH_LONG).show();
    }

}
