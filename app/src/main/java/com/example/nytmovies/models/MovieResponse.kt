package com.example.nytmovies.models


data class MovieResponse(
    val results: List<Result>,
    val copyright: String,
    val has_more: Boolean,
    val num_results: Int,
    val status: String
)


data class Result(
    val id: Int = 0,
    val display_title: String = "",
    val link: Link = Link("gfhns"),
    val mpaa_rating: String = "",
    val multimedia: Multimedia = Multimedia("ngf"),
    val publication_date: String = "",
    val summary_short: String = ""
) {
    // Empty constructor
    constructor() : this(0, "", Link("xfh"), "", Multimedia("ghs"), "", "")

    constructor(id: Int, display_title: String, link: Link) : this(id, display_title, link, "", Multimedia("hj"), "", "")
}

data class Link(
    val url: String
){
    constructor() : this("")
}

data class Multimedia(
    val src: String
) {
    constructor() : this("")


}