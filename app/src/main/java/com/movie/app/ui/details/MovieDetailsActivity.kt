package com.movie.app.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.movie.app.R
import com.movie.app.di.ApplicationComponent
import com.movie.app.di.HasApplicationComponent
import com.movie.app.di.MovieApplicationComponent
import com.movie.app.ui.base.BaseActivity
import com.movie.app.ui.details.di.MovieDetailsActivityComponent
import com.movie.app.ui.details.di.MovieDetailsActivityModule
import com.movie.app.ui.details.model.MovieDetailsViewModel
import com.movie.app.ui.di.ActivityComponent
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.loading_layout.*
import javax.inject.Inject
import android.net.Uri

class MovieDetailsActivity : BaseActivity<MovieDetailsView, MovieDetailsPresenter>(), MovieDetailsView {

    companion object {
        const val INTENT_MOVIE_ID = "MOVIE_ID"
    }

    @Inject
    lateinit var injectedPresenter: MovieDetailsPresenter

    private lateinit var activityComponent: MovieDetailsActivityComponent

    override fun inject() {
        activityComponent =
            (getApplicationComponent() as MovieApplicationComponent).add(MovieDetailsActivityModule())
        activityComponent.inject(this)
    }

    override fun getPresenter(): MovieDetailsPresenter = injectedPresenter

    override fun getView(): MovieDetailsView = this

    override fun getActivityComponent(): ActivityComponent = activityComponent

    override fun getApplicationComponent(): ApplicationComponent =
        (application as HasApplicationComponent).getApplicationComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setupToolbar()
        setupBookButton()

        val movieId = savedInstanceState?.getInt(INTENT_MOVIE_ID) ?: intent.getIntExtra(INTENT_MOVIE_ID, 0)
        fetchMovieDetails(movieId)
    }

    private fun fetchMovieDetails(movieId: Int) {
        if (movieId != 0) {
            injectedPresenter.fetchDetails(movieId)
        } else {
            finish()
        }
    }

    private fun setupToolbar() {
        collapsing_toolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary))
        collapsing_toolbar.title = getString(R.string.movie_details)
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar)
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar)
        collapsing_toolbar.isTitleEnabled = true

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupBookButton() {
        book_button.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://www.cathaycineplexes.com.sg/")
            startActivity(i)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val movieId = intent.getIntExtra(INTENT_MOVIE_ID, 0)
        outState?.putInt(INTENT_MOVIE_ID, movieId)
        super.onSaveInstanceState(outState)
    }

    override fun showLoading() {
        loading_view.visibility = View.VISIBLE
        main_content.visibility = View.GONE
        error_view.visibility = View.GONE
    }

    override fun showContent(model: MovieDetailsViewModel) {
        loading_view.visibility = View.GONE
        main_content.visibility = View.VISIBLE
        error_view.visibility = View.GONE

        Glide.with(this).load(model.backdropUrl).centerCrop().into(movie_backdrop)
        movie_title.text = model.title
        movie_synopsis.text = model.synopsis
        movie_genres.text = getString(R.string.genres_list, model.genres.joinToString())
        movie_languages.text = getString(R.string.language_list, model.languages.joinToString())
    }

    override fun showError() {
        loading_view.visibility = View.GONE
        main_content.visibility = View.GONE
        error_view.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        injectedPresenter.dispose()
        super.onDestroy()
    }
}