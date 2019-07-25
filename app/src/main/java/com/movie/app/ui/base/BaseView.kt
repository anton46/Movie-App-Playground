package com.movie.app.ui.base

interface BaseView<M> {
    fun showLoading()

    fun showContent(model: M)

    fun showError()
}