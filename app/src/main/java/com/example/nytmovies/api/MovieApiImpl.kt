package com.example.nytmovies.api

import com.example.nytmovies.models.MovieResponse
import com.example.nytmovies.utils.Resource
import javax.inject.Inject

class MovieApiImpl @Inject constructor(private val api: MovieApiService): MovieApi {
    override suspend fun getAllMovies(title: String): Resource<MovieResponse?> {
        val response = api.getMovies(title)
        return if (response.isSuccessful){
        Resource.success(response.body())
    }else{
        Resource.error("No news found", null)
    }
}

}