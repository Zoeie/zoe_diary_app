package com.zoe.diary.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.zoe.diary.utils.LogUtil;

/**
 * author zoe
 * created 2019/12/10 10:30
 */

/**
 * 图片可以在滑动中滚动，配合RecycleView使用
 */
public class MyAdImageView extends AppCompatImageView {

    /**
     * 使用方法：
     *      在监听中处理滑动
     *  mLinearLayoutManager = (LinearLayoutManager) rvUI.getLayoutManager();
     *         rvUI.addOnScrollListener(new RecyclerView.OnScrollListener() {
     *             @Override
     *             public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
     *                 super.onScrolled(recyclerView, dx, dy);
     *                 int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
     *                 int lPos = mLinearLayoutManager.findLastVisibleItemPosition();
     *                 for (int i = fPos; i <= lPos; i++) {
     *                     View view = mLinearLayoutManager.findViewByPosition(i);
     *                     MyAdImageView adImageView = view.findViewById(R.id.ad_view);
     *                     if (adImageView != null && adImageView.getVisibility() == View.VISIBLE) {
     *                         adImageView.setReferenceValue(view.getTop(), mLinearLayoutManager.getHeight());
     *                     }
     *                 }
     *             }
     *         });
     *
     *      <com.zoe.diary.ui.widget.MyAdImageView
     *         android:id="@+id/ad_view"
     *         android:layout_width="match_parent"
     *         android:layout_height="220dp"
     *         android:scaleType="matrix"
     *         android:src="@drawable/girl" />
     */

    private int translateY;
    private int height;

    public MyAdImageView(Context context) {
        super(context);
    }

    public MyAdImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAdImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    public void setReferenceValue(int value, int rvHeight) {
        if (getDrawable() == null) {
            return;
        }
        //计算应该滚动的距离，value为负值,证明图片在最上可见
        LogUtil.d("value:"+value+",rvHeight:"+rvHeight+",height:"+height);
        int shouldScrollHeight = (rvHeight - height);
        if (value <= 0) {
            value = Math.abs(value);
            translateY = (int) (shouldScrollHeight * (1.0f) * value / height);
        } /*else {
            int scrollSpaceY = (rvHeight - height);
            translateY = (int) (shouldScrollHeight * (1.0f) * (scrollSpaceY - value) / scrollSpaceY);
        }*/
        LogUtil.d("setReferenceValue translateY:"+translateY);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.d("onDraw translateY:"+translateY);
        Drawable drawable = getDrawable();
        int w = getWidth();
        int h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, w, h);
        canvas.save();
        canvas.translate(0, -translateY);
        super.onDraw(canvas);
        canvas.restore();
    }

    /**
     * 在RecycleView中使用时，存在复用，导致显示的时候，是已经translate之后的效果，在显示前，重置
     */
    public void reset() {
        translateY = 0;
        invalidate();
    }
}
