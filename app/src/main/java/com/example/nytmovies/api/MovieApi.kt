package com.example.nytmovies.api

import com.example.nytmovies.models.MovieResponse
import com.example.nytmovies.utils.Resource

interface MovieApi {
    suspend fun getAllMovies(title: String): Resource<MovieResponse?>


}