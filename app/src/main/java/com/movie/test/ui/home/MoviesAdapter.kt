package com.movie.test.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movie.test.R
import com.movie.test.ui.home.model.MovieViewModel

class MoviesAdapter(private val onMovieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<MoviesAdapter.MovieItemViewHolder>() {

    private var items: MutableList<MovieViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MovieItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_movie_item, parent, false)
        return MovieItemViewHolder(itemView)
    }

    fun addMovies(movies: List<MovieViewModel>) {
        items.addAll(movies)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bindView(items[position], onMovieClickListener)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context = itemView.context
        private val movieItemContainer = itemView.findViewById<View>(R.id.movie_item_container)
        private val movieTitle = itemView.findViewById<TextView>(R.id.movie_title)
        private val moviePoster = itemView.findViewById<ImageView>(R.id.movie_poster)
        private val moviePopularity = itemView.findViewById<TextView>(R.id.movie_popularity)

        fun bindView(item: MovieViewModel, onMovieClickListener: OnMovieClickListener) {
            movieItemContainer.setOnClickListener { onMovieClickListener.onMovieClicked(item.movieId) }
            movieTitle.text = item.title
            moviePopularity.text = context.resources.getString(R.string.popularity, item.popularity)
            Glide.with(moviePoster).load(item.posterUrl).centerCrop().into(moviePoster)
        }
    }
}