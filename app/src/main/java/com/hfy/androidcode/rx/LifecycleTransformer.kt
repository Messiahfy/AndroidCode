package com.hfy.androidcode.rx

import io.reactivex.*
import org.reactivestreams.Publisher
import java.util.concurrent.CancellationException

/**
 * created by huangfeiyang on 2020-02-25
 *
 * RxJava的转换器，为上游添加结束监听，以在适时结束事件流
 */
class LifecycleTransformer<T>(private val observable: Observable<*>) :
    ObservableTransformer<T, T>,
    FlowableTransformer<T, T>,
    SingleTransformer<T, T>,
    MaybeTransformer<T, T>,
    CompletableTransformer {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.takeUntil(observable)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.takeUntil(observable.toFlowable(BackpressureStrategy.LATEST))
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.takeUntil(observable.firstOrError())
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.takeUntil(observable.firstElement())
    }

    override fun apply(upstream: Completable): CompletableSource {
        return Completable.ambArray(
            upstream,
            observable.concatMapCompletable { Completable.error(CancellationException()) }
        )
    }
}