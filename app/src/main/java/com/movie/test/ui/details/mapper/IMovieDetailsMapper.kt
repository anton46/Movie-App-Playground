package com.movie.test.ui.details.mapper

import com.movie.test.domain.data.response.MovieDetailResponse
import com.movie.test.ui.details.model.MovieDetailsViewModel

interface IMovieDetailsMapper {
    fun map(movieDetailResponse: MovieDetailResponse): MovieDetailsViewModel
}