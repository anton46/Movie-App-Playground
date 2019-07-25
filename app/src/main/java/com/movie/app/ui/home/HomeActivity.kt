package com.movie.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.movie.app.R
import com.movie.app.di.ApplicationComponent
import com.movie.app.di.HasApplicationComponent
import com.movie.app.di.MovieApplicationComponent
import com.movie.app.ui.base.BaseActivity
import com.movie.app.ui.details.MovieDetailsActivity
import com.movie.app.ui.di.ActivityComponent
import com.movie.app.ui.home.di.HomeActivityComponent
import com.movie.app.ui.home.di.HomeActivityModule
import com.movie.app.ui.home.model.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.loading_layout.*
import javax.inject.Inject

class HomeActivity : BaseActivity<HomeView, HomePresenter>(), HomeView, OnMovieClickListener {

    @Inject
    lateinit var injectedPresenter: HomePresenter

    @Inject
    lateinit var adapter: MoviesAdapter

    private lateinit var activityComponent: HomeActivityComponent

    override fun inject() {
        activityComponent = (getApplicationComponent() as MovieApplicationComponent).add(HomeActivityModule(this))
        activityComponent.inject(this)
    }

    override fun getPresenter(): HomePresenter = injectedPresenter

    override fun getView(): HomeView = this

    override fun getActivityComponent(): ActivityComponent = activityComponent

    override fun getApplicationComponent(): ApplicationComponent =
        (application as HasApplicationComponent).getApplicationComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        initRecyclerView()
        load()
    }

    private fun setupToolbar() {
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun load() {
        injectedPresenter.load()
    }

    override fun showLoading() {
        loading_view.visibility = View.VISIBLE
        content_view.visibility = View.GONE
        error_view.visibility = View.GONE
    }

    override fun showContent(model: HomeViewModel) {
        content_view.isRefreshing = false
        content_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE

        adapter.addMovies(model.movies)
    }

    override fun showError() {
        error_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        content_view.visibility = View.GONE
    }

    override fun onLoadMore() {
        Snackbar.make(movie_list, R.string.loading_more_movies, Snackbar.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        movie_list.setHasFixedSize(true)
        movie_list.layoutManager = GridLayoutManager(this, 2)
        movie_list.adapter = adapter
        movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    injectedPresenter.nextPage()
                }
            }
        })

        content_view.setOnRefreshListener {
            adapter.clear()
            injectedPresenter.refresh()
        }
    }

    override fun onDestroy() {
        injectedPresenter.dispose()
        super.onDestroy()
    }

    override fun onMovieClicked(movieId: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MovieDetailsActivity.INTENT_MOVIE_ID, movieId)
        startActivity(intent)
    }
}
