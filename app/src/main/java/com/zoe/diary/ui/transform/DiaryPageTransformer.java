package com.zoe.diary.ui.transform;

import android.os.Build;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class DiaryPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.9f;
    private static final  float MIN_ALPHA = 0.5f;
    private static final  float MIN_TranslationZ= 0.8f;
    private static final  float MIN_TRANSLATE = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) { //不是最前面的三张图片的话，设置默认
            //page.setAlpha(MIN_ALPHA);
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else {
            //应该缩放的大小
            float curScale = (1 - Math.abs(position)) * (1 - MIN_SCALE) + MIN_SCALE;
            curScale = Math.max(curScale, MIN_SCALE);
            float curAlpha = (1 - Math.abs(position)) * (1 - MIN_ALPHA) + MIN_ALPHA;
            curAlpha = Math.max(curAlpha, MIN_ALPHA);
            //page.setAlpha(curAlpha);
            page.setScaleX(curScale);
            page.setScaleY(curScale);
            float curTranZ = (1 - Math.abs(position)) * (1 - MIN_ALPHA) + MIN_ALPHA;
            curTranZ = Math.max(curTranZ, MIN_TranslationZ);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                page.setTranslationZ(curTranZ);
            }
        }
    }
}
