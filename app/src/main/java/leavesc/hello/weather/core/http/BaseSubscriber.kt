package leavesc.hello.weather.core.http

import io.reactivex.observers.DisposableObserver
import leavesc.hello.weather.core.holder.ToastHolder
import leavesc.hello.weather.core.http.model.OptionT

/**
 * 作者：leavesC
 * 时间：2019/5/31 11:03
 * 描述：
 */
class BaseSubscriber<T> constructor(private val requestCallback: RequestCallback<T>) :
    DisposableObserver<OptionT<T>>() {

    override fun onNext(t: OptionT<T>) {
        requestCallback.onSuccess(t.value)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        val msg = e.message ?: "未知错误"
        if (requestCallback is RequestMultiplyCallback) {
            if (e is BaseException) {
                requestCallback.onFail(e)
            } else {
                requestCallback.onFail(ServerResultException(msg))
            }
        } else {
            ToastHolder.showToast(msg = msg)
        }
    }

    override fun onComplete() {

    }

}