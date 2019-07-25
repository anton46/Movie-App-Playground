package com.movie.test.ui.home

import com.movie.test.domain.repository.IMovieRepository
import com.movie.test.net.rx.ISchedulerFactory
import com.movie.test.ui.base.BasePresenter
import com.movie.test.ui.home.mapper.IHomeViewModelMapper
import com.movie.test.ui.home.model.HomeViewModel

class HomePresenter(
    private val repository: IMovieRepository,
    private val schedulerFactory: ISchedulerFactory,
    private val mapper: IHomeViewModelMapper
) : BasePresenter<HomeView, HomeViewModel>() {

    init {
        setModel(HomeViewModel())
    }

    fun load(page: Int = 1) {
        subscribe {
            repository.fetchMovies(page)
                .map(mapper::map)
                .map { it.apply { this.pageNumber = page } }
                .observeOn(schedulerFactory.main())
                .subscribeOn(schedulerFactory.io())
                .subscribe(::onLoaded) { getView().showError() }
        }.showLoadingOr(
            { it.pageNumber > 1 }, HomeView::onLoadMore
        )
    }

    fun nextPage() {
        getViewModel().incrementPageAndLoad()
    }

    fun refresh() {
        getViewModel().refresh()
    }

    private fun onLoaded(homeViewModel: HomeViewModel) {
        setModel(homeViewModel)
        getView().showContent(getViewModel())
    }

    private fun HomeViewModel.incrementPageAndLoad() {
        this.pageNumber += 1
        load(this.pageNumber)
    }

    private fun HomeViewModel.refresh() = this.run {
        this.pageNumber = 1
        load()
    }
}
