package com.base.retrofitmoviesxml.domain.api

import com.base.retrofitmoviesxml.domain.response.MoviesListResponse
import com.base.retrofitmoviesxml.utils.ApiConstants.Companion.MOVIE_POPULAR
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET(MOVIE_POPULAR)
    fun getPopularMovies(
        @Query("page") page: Int
    ): Call<MoviesListResponse>


}