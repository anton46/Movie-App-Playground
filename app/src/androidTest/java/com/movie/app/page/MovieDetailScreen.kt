package com.movie.app.page

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.movie.app.R

class MovieDetailScreen : Screen<MovieDetailScreen>() {
    val synopsis: KTextView = KTextView { withId(R.id.movie_synopsis) }
    val bookButton: KTextView = KTextView { withId(R.id.book_button) }
}