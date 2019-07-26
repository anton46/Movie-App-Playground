package com.movie.app.ui.details.mapper

import com.movie.app.net.data.response.MovieDetailResponse
import com.movie.app.ui.details.model.MovieDetailsViewModel

interface IMovieDetailsMapper {
    fun map(movieDetailResponse: MovieDetailResponse): MovieDetailsViewModel
}