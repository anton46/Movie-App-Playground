package com.movie.app.ui.home.model

data class HomeViewModel(
    val movies: List<MovieViewModel> = listOf()
) {
    var pageNumber: Int = 1
}
