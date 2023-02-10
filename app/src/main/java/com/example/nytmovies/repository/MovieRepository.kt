package com.example.nytmovies.repository

import com.example.nytmovies.api.MovieApi
import com.example.nytmovies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi){
    suspend fun getMovies() = flow {
        emit(Resource.loading(null))
        emit(api.getAllMovies())
    }.flowOn(Dispatchers.IO)
}