package com.hfy.androidcode.rx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


/**
 * created by huangfeiyang on 2020-02-25
 *
 * 配合Jetpack的Lifecycles框架，监听生命周期，以在必要时候取消RxJava的订阅，防止内存泄漏
 */
class RxLifecycleObserver private constructor(private val lifecycleOwner: LifecycleOwner) :
    LifecycleObserver {

    private val subject: Subject<LifecycleEvent> by lazy { PublishSubject.create<LifecycleEvent>() }

    companion object {
        /**
         * 创建RxLifecycleObserver，绑定LifecycleOwner
         */
        fun with(lifecycleOwner: LifecycleOwner): RxLifecycleObserver {
            return RxLifecycleObserver(lifecycleOwner)
        }
    }

    /**
     * bindLifecycle针对 onDestroy 事件的包装函数
     */
    fun <T> bindOnDestroy(): LifecycleTransformer<T> {
        return bindLifecycle(LifecycleEvent.ON_DESTROY)
    }

    /**
     * 绑定某个具体生命周期，监听到该生命周期事件时，将结束RxJava的事件流
     */
    fun <T> bindLifecycle(lifecycleEvent: LifecycleEvent): LifecycleTransformer<T> {
        lifecycleOwner.lifecycle.addObserver(this)
        return LifecycleTransformer(subject.filter { it == lifecycleEvent })
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        subject.onNext(LifecycleEvent.ON_PAUSE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        subject.onNext(LifecycleEvent.ON_STOP)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        subject.onNext(LifecycleEvent.ON_DESTROY)
    }
}