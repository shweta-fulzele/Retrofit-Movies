package com.base.retrofitmoviesxml.domain.api

import com.base.retrofitmoviesxml.domain.response.MoviesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Call<MoviesListResponse>


}