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

    // Interceptor to pass API Key in the URL
    private val requestInterceptor = Interceptor { chain ->
        // Get the original URL from the request
        val url = chain.request()
            .url
            .newBuilder()
            // Add the API key as a query parameter
            .addQueryParameter("api_key", API_KEY)
            .build()

        // Build a new request with the modified URL
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        // Proceed with the modified request
        return@Interceptor chain.proceed(request)
    }

    // Add interceptor to OkHttpClient
    private fun createOkHttpClient(): OkHttpClient {
        // Create an OkHttpClient with a timeout, and add the requestInterceptor
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    // Create Retrofit instance
    fun createRetrofitInstance(): Retrofit {
        // Create an OkHttpClient with the configured interceptors
        val okHttpClient = createOkHttpClient()

        // Build a Retrofit instance with the base URL, OkHttpClient, and Gson converter factory
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Return the configured Retrofit instance
        return retrofit
    }
}
