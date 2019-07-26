package com.movie.app.ui.details.mapper

import com.movie.app.net.data.response.MovieDetailResponse
import com.movie.app.net.factory.ImageUrlFactory
import com.movie.app.ui.details.model.MovieDetailsViewModel

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