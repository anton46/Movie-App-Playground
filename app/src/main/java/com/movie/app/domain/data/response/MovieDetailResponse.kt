package com.movie.app.domain.data.response

import com.google.gson.annotations.SerializedName
import com.movie.app.domain.data.entity.Genre
import com.movie.app.domain.data.entity.Language

data class MovieDetailResponse(
    @SerializedName("title") val title: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("spoken_languages") val spokenLanguages: List<Language>,
    @SerializedName("overview") val synopsis: String
)