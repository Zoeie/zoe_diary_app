package com.zoe.diary.net.contract;

import io.reactivex.disposables.Disposable;

/**
 * author zoe
 * created 2019/11/7 11:18
 */

public interface IContract {

    interface IPresenter<V extends IView> {

        /**
         * 绑定视图
         *
         * @param view
         */
        void attachView(V view);

        /**
         * 解除视图绑定
         */
        void detachView();

        /**
         * 检查视图是否已经绑定
         *
         * @return
         */
        boolean isAttachView();

        /**
         * 获取视图
         *
         * @return
         */
        V getView();

        void addDisposable(Disposable disposable);

        void removeDisposable(Disposable disposable);

        void removeAllDisposable();
    }

    interface IView {
    }

    interface IModel<T> {
        /**
         * 发起一个请求
         *
         * @return
         */
        T doRxRequest();

        /**
         * 发起一个请求
         *
         * @return
         */
        T doRxRequest(String baseUrl);
    }
}
