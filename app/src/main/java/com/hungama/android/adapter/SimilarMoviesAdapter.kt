package com.hungama.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hungama.android.data.model.Movies
import com.hungama.android.data.model.SimilarMovie
import com.hungama.android.databinding.LayoutMoviesListItemBinding
import com.hungama.android.databinding.LayoutSimilarMoviesItemBinding
import com.hungama.android.utils.Constants
import com.hungama.android.utils.DownloadImageTask


class SimilarMoviesAdapter(var similarMovies : ArrayList<SimilarMovie.Result>, var context: Context) :
    RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutSimilarMoviesItemBinding = LayoutSimilarMoviesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setAdapter(similarMovies: ArrayList<SimilarMovie.Result>) {
        this.similarMovies = similarMovies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return similarMovies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvSimilarMovieTitle.text = similarMovies[position].title
        DownloadImageTask(holder.binding.ivSimilarMovie).execute(Constants.BASE_IMAGE_URL + similarMovies[position].posterPath);
    }

    class ViewHolder(var binding: LayoutSimilarMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}