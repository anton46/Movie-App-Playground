package com.movie.test.ui.details.model

data class MovieDetailsViewModel(
    val title: String,
    val backdropUrl: String,
    val synopsis: String,
    val languages: List<String>,
    val genres: List<String>
)