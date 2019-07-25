package com.movie.app.ui.home.mapper

import com.movie.app.domain.data.response.MoviesResponse
import com.movie.app.net.factory.ImageUrlFactory
import com.movie.app.ui.home.model.HomeViewModel
import com.movie.app.ui.home.model.MovieViewModel

class HomeViewModelMapper(private val imageUrlFactory: ImageUrlFactory) : IHomeViewModelMapper {
    override fun map(moviesResponse: MoviesResponse) =
        moviesResponse.results
            .filter { it.posterUrl != null }
            .map {
                MovieViewModel(
                    it.movieId,
                    imageUrlFactory.createPosterUrl(it.posterUrl.orEmpty()),
                    it.title,
                    it.popularity
                )
            }
            .toHomeViewModel()

    private fun List<MovieViewModel>.toHomeViewModel() = HomeViewModel(this)
}