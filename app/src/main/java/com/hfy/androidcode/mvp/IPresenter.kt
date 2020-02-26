package com.hfy.androidcode.mvp

/**
 * created by huangfeiyang on 2020-02-11
 */
interface IPresenter<V : IView> {
    fun attachView(v: V)
    fun detachView(v: V)
}