package com.example.nytmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytmovies.models.MovieResponse
import com.example.nytmovies.repository.MovieRepository
import com.example.nytmovies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository): ViewModel(){
    private var movieItemLiveData = MutableLiveData<Resource<MovieResponse?>>()
    private val _results = MutableLiveData<List<com.example.nytmovies.models.Result>>()

    val results: LiveData<List<com.example.nytmovies.models.Result>>
        get() = _results
    init {
        getMovies("godfather")
    }

   fun getMovies(title: String) = viewModelScope.launch{
        repository.getMovies(title).collect(){ response ->
            movieItemLiveData.postValue(response)
        }

    }

    fun observeMovieLiveData(): LiveData<Resource<MovieResponse?>>{
        return movieItemLiveData
    }



}