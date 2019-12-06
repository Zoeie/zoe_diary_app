package com.zoe.diary.ui.activity.base;

import android.os.Bundle;

import com.zoe.diary.net.base.BasePresenter;
import com.zoe.diary.net.contract.IContract;

/**
 * author zoe
 * created 2019/6/19 9:42
 */

public abstract class BaseMVPActivity<P extends BasePresenter<V>, V extends IContract.IView> extends BaseActivity {
    protected P mPresenter;

    /**
     * 创建Presenter实例
     *
     * @return
     */
    protected abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        attachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachView();
    }

    /**
     * 绑定
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    /**
     * 解绑
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
