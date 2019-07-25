package com.movie.test.ui.base

interface BaseView<M> {
    fun showLoading()

    fun showContent(model: M)

    fun showError()
}