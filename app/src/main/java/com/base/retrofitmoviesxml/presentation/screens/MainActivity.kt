package com.base.retrofitmoviesxml.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.movieapplication.domain.response.MoviesListResponse
import com.base.retrofitmoviesxml.databinding.ActivityMainBinding
import com.base.retrofitmoviesxml.domain.api.ApiClient
import com.base.retrofitmoviesxml.domain.api.ApiServices
import com.base.retrofitmoviesxml.presentation.adapter.MoviesAdapter
import com.base.retrofitmoviesxml.utils.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val moviesAdapter by lazy { MoviesAdapter() }

    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitViews
        binding.apply {
            //show loading
            pbMovies.visibility = View.VISIBLE

            // Set the adapter for rvMovies here
            rlMovies.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = moviesAdapter
            }

            // Call movies API
            val callMoviesApi = api.getPopularMovie(1)
            callMoviesApi.enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(call: Call<MoviesListResponse>, response: Response<MoviesListResponse>) {
                    pbMovies.visibility = View.GONE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        moviesAdapter.differ.submitList(itData)
                                        // No need to set the adapter again here
                                    }
                                }
                            }
                        }
                        // Handle other cases
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    pbMovies.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }
            })
        }
    }
}
