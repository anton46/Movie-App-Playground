package com.movie.test.ui.home.mapper

import com.movie.test.domain.data.response.MoviesResponse
import com.movie.test.net.factory.ImageUrlFactory
import com.movie.test.ui.home.model.HomeViewModel
import com.movie.test.ui.home.model.MovieViewModel

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