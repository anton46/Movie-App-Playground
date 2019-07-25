package com.movie.app.ui.details

import com.movie.app.domain.repository.IMovieRepository
import com.movie.app.net.rx.ISchedulerFactory
import com.movie.app.ui.base.BasePresenter
import com.movie.app.ui.details.mapper.IMovieDetailsMapper
import com.movie.app.ui.details.model.MovieDetailsViewModel

class MovieDetailsPresenter(
    private val repository: IMovieRepository,
    private val schedulerFactory: ISchedulerFactory,
    private val mapper: IMovieDetailsMapper
) : BasePresenter<MovieDetailsView, MovieDetailsViewModel>() {

    fun fetchDetails(movieId: Int) {
        subscribe {
            repository.fetchMovieDetails(movieId)
                .map(mapper::map)
                .observeOn(schedulerFactory.main())
                .subscribeOn(schedulerFactory.io())
                .subscribe({ result -> getView().showContent(result) }, { getView().showError() })
        }.showLoading()
    }
}