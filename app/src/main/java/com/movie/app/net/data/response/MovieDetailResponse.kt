package com.movie.app.net.data.response

import com.google.gson.annotations.SerializedName
import com.movie.app.net.data.entity.Genre
import com.movie.app.net.data.entity.Language

data class MovieDetailResponse(
    @SerializedName("title") val title: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("spoken_languages") val spokenLanguages: List<Language>,
    @SerializedName("overview") val synopsis: String
)