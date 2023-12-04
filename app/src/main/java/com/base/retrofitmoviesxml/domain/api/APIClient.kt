package com.base.retrofitmoviesxml.domain.api

import com.base.retrofitmoviesxml.utils.Constants.API_KEY
import com.base.retrofitmoviesxml.utils.Constants.BASE_URL
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    private lateinit var retrofit: Retrofit

//    Interceptor to pass API Key in the url
    private val requestInterceptor = Interceptor{chain ->

    val url = chain.request()
        .url
        .newBuilder()
        .addQueryParameter("api_key", API_KEY)
        .build()

    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()

    return@Interceptor chain.proceed(request)


    }

//    Add interceptor to OkHttpClient
    private fun createOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    fun createRetrofitInstance(): Retrofit{
        val okHttpClient = createOkHttpClient()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

}