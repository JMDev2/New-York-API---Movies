package com.example.nytmovies.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class MovieResponse(
    val results: List<Result>,
    val copyright: String,
    val has_more: Boolean,
    val num_results: Int,
    val status: String
)


data class Result(
    val byline: String,
    val critics_pick: Int,
    val date_updated: String,
    val display_title: String,
    val headline: String,
    val link: Link,
    val mpaa_rating: String,
    val multimedia: Multimedia,
    val opening_date: String,
    val publication_date: String,
    val summary_short: String
)


data class Link(
    val suggested_link_text: String,
    val type: String,
    val url: String
)

data class Multimedia(
    val height: Int,
    val src: String,
    val type: String,
    val width: Int
)