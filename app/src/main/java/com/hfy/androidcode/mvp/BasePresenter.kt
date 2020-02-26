package com.hfy.androidcode.mvp

import android.content.Context

/**
 * created by huangfeiyang on 2020-02-11
 */
abstract class BasePresenter<V : IView> : IPresenter<V> {
    var mView: V? = null

    override fun attachView(v: V) {
        mView = v
    }

    override fun detachView(v: V) {
        mView = null
    }

    fun getContext(): Context {
        return mView!!.getContext()
    }
}