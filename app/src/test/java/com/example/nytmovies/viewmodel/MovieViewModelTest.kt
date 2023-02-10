package com.example.nytmovies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nytmovies.MockFileReader
import com.example.nytmovies.TestCoroutineRule
import com.example.nytmovies.models.MovieResponse
import com.example.nytmovies.repository.MovieRepository
import com.example.nytmovies.utils.Resource
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MovieViewModelTest {
    @Rule
    @JvmField

    val instantRunExecutorRule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRepository: MovieRepository
    private val response = MockFileReader("campaign_response.json").content


    @Before
    fun setUp() {
        movieRepository = mockk()
        movieViewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun `for multiple movies`(){
        val jsonResponse = Gson().fromJson(response, MovieResponse::class.java)
        val responseResource = Resource.success(jsonResponse)
        coEvery { movieRepository.getMovies() } returns flow {
            emit (Resource.loading(null))
            emit(responseResource)
        }
        movieViewModel.getMovies()
        val movies = movieViewModel.observeMovieLiveData().value
        assertEquals(responseResource, movies)
    }
}