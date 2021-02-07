package com.hungama.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hungama.android.data.model.MovieVideo
import com.hungama.android.databinding.LayoutMovieVideoItemBinding
import com.hungama.android.databinding.LayoutMoviesListItemBinding
import com.hungama.android.utils.Constants
import com.hungama.android.utils.DownloadImageTask


class MovieVideoAdapter(var movieVideos: ArrayList<MovieVideo.Result>, var context: Context) :
    RecyclerView.Adapter<MovieVideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutMovieVideoItemBinding = LayoutMovieVideoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setAdapter(movieVideos: ArrayList<MovieVideo.Result>) {
        this.movieVideos = movieVideos
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieVideos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        DownloadImageTask(holder.binding.ivMovieVideo).execute(Constants.BASE_IMAGE_URL + movieVideos[position].);
    }

    class ViewHolder(var binding: LayoutMovieVideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}