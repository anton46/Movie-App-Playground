package com.movie.test.ui.home.mapper

import com.movie.test.domain.data.response.MoviesResponse
import com.movie.test.ui.home.model.HomeViewModel

interface IHomeViewModelMapper {
    fun map(moviesResponse: MoviesResponse): HomeViewModel
}