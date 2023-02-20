package com.example.nytmovies.utils


import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        return try {
            chain.proceed(builder.build())
        }catch (e:Exception){
            Response.Builder()
                .request(builder.build())
                .code(505)
                .protocol(Protocol.HTTP_1_0)
                .body("".toResponseBody("application/json".toMediaTypeOrNull()))
                .message("")
                .build()
        }

    }
}