package com.hfy.androidcode.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * mvp fragment 基类
 */
abstract class MvpFragment<V : IView, P : BasePresenter<V>> : Fragment(), IView {
    lateinit var mPresenter: P

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!this::mPresenter.isInitialized) {
            mPresenter = createPresenter()
        }
        mPresenter.attachView(this as V)
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView(this as V)
    }

    abstract fun createPresenter(): P

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun getContext() = activity!!
}
