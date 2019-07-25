package com.movie.app.domain.data.entity

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("iso_3166_1") val iso: String,
    @SerializedName("name") val languageName: String
)