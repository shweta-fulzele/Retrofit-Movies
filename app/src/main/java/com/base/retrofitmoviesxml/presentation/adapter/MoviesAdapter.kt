package com.base.retrofitmoviesxml.presentation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.base.movieapplication.domain.response.MoviesListResponse
import com.base.retrofitmoviesxml.R
import com.base.retrofitmoviesxml.databinding.ItemMoviesBinding
import com.base.retrofitmoviesxml.presentation.screens.MovieDetailsActivity
import com.base.retrofitmoviesxml.utils.Constants.POSTER_BASE_URL

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private lateinit var binding:ItemMoviesBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMoviesBinding.inflate(inflater,parent, false)
        context = parent.context

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class ViewHolder(binding: ItemMoviesBinding) : RecyclerView.ViewHolder(this.binding.root) {

        fun bind(item : MoviesListResponse.Result){
            binding.apply {
                tvMovieName.text = item.title
                tvMovieDateRelease.text = item.releaseDate
                tvRate.text=item.voteAverage.toString()
                val moviePosterURL = POSTER_BASE_URL + item.posterPath
                ImgMovie.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvLang.text=item.originalLanguage

                root.setOnClickListener {
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra("id", item.id)
                    context.startActivity(intent)
                }
            }
        }


    }


    private val differCallback = object : DiffUtil.ItemCallback<MoviesListResponse.Result>(){
        override fun areItemsTheSame(
            oldItem: MoviesListResponse.Result,
            newItem: MoviesListResponse.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MoviesListResponse.Result,
            newItem: MoviesListResponse.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
}