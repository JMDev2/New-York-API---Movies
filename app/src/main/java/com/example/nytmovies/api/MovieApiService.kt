package com.example.nytmovies.api

import com.example.nytmovies.constants.Constants
import com.example.nytmovies.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("reviews/search.json")
    suspend fun getMovies(
        @Query("godfather") title: String,
        @Query("api-key") apiKey: String = Constants.API_KEY
    ): Response<MovieResponse>
}