package com.movie.test.ui.details.mapper

import com.movie.test.domain.data.response.MovieDetailResponse
import com.movie.test.net.factory.ImageUrlFactory
import com.movie.test.ui.details.model.MovieDetailsViewModel

class MovieDetailsMapper(private val imageUrlFactory: ImageUrlFactory) : IMovieDetailsMapper {

    override fun map(movieDetailResponse: MovieDetailResponse): MovieDetailsViewModel {
        return MovieDetailsViewModel(
            movieDetailResponse.title,
            imageUrlFactory.createBackdropUrl(movieDetailResponse.backdropPath),
            movieDetailResponse.synopsis,
            movieDetailResponse.spokenLanguages.map { it.languageName },
            movieDetailResponse.genres.map { it.name }
        )
    }
}