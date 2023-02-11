package com.example.nytmovies.api

import com.example.nytmovies.MockFileReader
import com.example.nytmovies.utils.Status
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


@RunWith(JUnit4::class)
class MovieApiImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieApiImpl: MovieApiImpl
    private var okHttpClient = OkHttpClient.Builder()
        .build()
    private val response = MockFileReader("campaign_response.json").content

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val movieApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
        movieApiImpl = MovieApiImpl(movieApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `for multiple movies returned` () = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(response)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = movieApiImpl.getAllMovies("Godfather")
        assertEquals(Status.SUCCESS, actualResponse.status)
    }

    @Test
    fun `for server error` () = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = movieApiImpl.getAllMovies("Godfather")
        assertEquals(Status.ERROR, actualResponse.status)
    }
}