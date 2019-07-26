package com.movie.app.ui.home.mapper

import com.movie.app.net.data.response.MoviesResponse
import com.movie.app.ui.home.model.HomeViewModel

interface IHomeViewModelMapper {
    fun map(moviesResponse: MoviesResponse): HomeViewModel
}