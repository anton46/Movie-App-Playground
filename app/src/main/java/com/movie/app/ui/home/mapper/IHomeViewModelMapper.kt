package com.movie.app.ui.home.mapper

import com.movie.app.domain.data.response.MoviesResponse
import com.movie.app.ui.home.model.HomeViewModel

interface IHomeViewModelMapper {
    fun map(moviesResponse: MoviesResponse): HomeViewModel
}