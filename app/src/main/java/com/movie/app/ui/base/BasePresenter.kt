package com.movie.app.ui.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

open class BasePresenter<V : BaseView<M>, M : Any> {
    private lateinit var view: V
    private lateinit var model: M

    private val compositeSubscription = CompositeSubscription()

    fun attachView(view: V) {
        this.view = view
    }

    fun getView(): V = view

    fun getViewModel(): M = model

    fun setModel(model: M) {
        this.model = model
    }

    fun subscribe(receiver: () -> Subscription) {
        compositeSubscription.add(receiver.invoke())
    }

    fun Any?.showLoadingOr(showOther: (M) -> Boolean, other: (V) -> Unit) =
        if (showOther(getViewModel())) other(getView()) else showLoading()

    fun Any?.showLoading() = getView().showLoading()

    fun dispose() {
        if (compositeSubscription.hasSubscriptions()) compositeSubscription.clear()
    }
}