package com.zoe.diary.net.base;


import com.zoe.diary.net.contract.IContract;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends IContract.IView> implements IContract.IPresenter<V> {
    private WeakReference<V> mReference;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(V view) {
        mReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mReference != null) {
            mReference.clear();
            mReference = null;
        }
        removeAllDisposable();
    }

    @Override
    public boolean isAttachView() {
        if (mReference == null || mReference.get() == null)
            throw new RuntimeException("You have not bound this view");
        return mReference != null && mReference.get() != null;
    }

    @Override
    public V getView() {
        if (mReference == null) return null;
        return mReference.get();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void removeDisposable(Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.remove(disposable);
        }
    }

    @Override
    public void removeAllDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
