package com.hfy.androidcode.rx

/**
 * created by huangfeiyang on 2020-02-25
 *
 * 生命周期，用于RxJava监听生命周期结束。可对应到Activity和Fragment
 */
enum class LifecycleEvent {
    /**
     * onPause 事件
     */
    ON_PAUSE,
    /**
     * onStop 事件
     */
    ON_STOP,
    /**
     * onDestroy 事件
     */
    ON_DESTROY
}