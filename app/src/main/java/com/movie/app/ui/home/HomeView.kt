package com.movie.app.ui.home

import com.movie.app.ui.base.BaseView
import com.movie.app.ui.home.model.HomeViewModel

interface HomeView : BaseView<HomeViewModel> {
    fun onLoadMore()
}