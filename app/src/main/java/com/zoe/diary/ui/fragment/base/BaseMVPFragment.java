package com.zoe.diary.ui.fragment.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zoe.diary.net.base.BasePresenter;
import com.zoe.diary.net.contract.IContract;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author zoe
 * created 2019/12/6 15:35
 */

public abstract class BaseMVPFragment<P extends BasePresenter<V>, V extends IContract.IView> extends BaseFragment {


    protected P mPresenter;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        attachView();
    }

    /**
     * 创建Presenter实例
     *
     * @return
     */
    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
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
