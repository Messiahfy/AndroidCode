package com.hfy.androidcode.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class MvpActivity<V : IView, P : BasePresenter<V>> : AppCompatActivity(), IView {
    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView(this as V)
    }

    abstract fun createPresenter(): P

    override fun getContext() = this
}