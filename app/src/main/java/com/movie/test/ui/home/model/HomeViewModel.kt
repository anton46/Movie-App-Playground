package com.movie.test.ui.home.model

data class HomeViewModel(
    val movies: List<MovieViewModel> = listOf()
) {
    var pageNumber: Int = 1
}
