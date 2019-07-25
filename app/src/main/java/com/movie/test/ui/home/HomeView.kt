package com.movie.test.ui.home

import com.movie.test.ui.base.BaseView
import com.movie.test.ui.home.model.HomeViewModel

interface HomeView : BaseView<HomeViewModel> {
    fun onLoadMore()
}