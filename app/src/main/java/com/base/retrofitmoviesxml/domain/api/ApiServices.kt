package com.base.retrofitmoviesxml.domain.api

import com.base.movieapplication.domain.response.MovieDetails
import com.base.movieapplication.domain.response.MoviesListResponse
import com.base.retrofitmoviesxml.utils.ApiConstants.Companion.MOVIE_POPULAR
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    //    https://api.themoviedb.org/3/movie/550?api_key=***
    //    https://api.themoviedb.org/3/movie/popular?api_key=***
    //    https://api.themoviedb.org/3/

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Call<MovieDetails>

    @GET(MOVIE_POPULAR)
    fun getPopularMovie(@Query("page") page: Int): Call<MoviesListResponse>

}