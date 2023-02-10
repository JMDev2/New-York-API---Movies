package com.example.nytmovies.di

import com.example.nytmovies.api.MovieApi
import com.example.nytmovies.api.MovieApiImpl
import com.example.nytmovies.api.MovieApiService
import com.example.nytmovies.constants.Constants.Companion.BASE_URL
import com.example.nytmovies.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .build()

    @Singleton
    @Provides
    fun getRetrofitInstance(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): MovieApiService{
        return retrofit.create(MovieApiService::class.java)
    }

    @Singleton
    @Provides
    fun getMovieApi(movieApiService: MovieApiService): MovieApi{
        return MovieApiImpl(movieApiService)
    }

    @Singleton
    @Provides
    fun getMovieRepository(movieApi: MovieApi): MovieRepository{
        return MovieRepository(movieApi)
    }
}