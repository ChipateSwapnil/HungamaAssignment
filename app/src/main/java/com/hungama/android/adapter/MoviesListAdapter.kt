package com.hungama.android.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hungama.android.data.model.Movies
import com.hungama.android.databinding.LayoutMoviesListItemBinding
import com.hungama.android.utils.Constants
import com.hungama.android.utils.DownloadImageTask
import com.hungama.android.view.MovieDetailActivity


class MoviesListAdapter(
    var movies: ArrayList<Movies.Result>,
    var context: Context
) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutMoviesListItemBinding = LayoutMoviesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setAdapter(movies: ArrayList<Movies.Result>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var movieId = movies[position].id
        holder.binding.tvMovieTitle.text = movies[position].title
        holder.binding.tvReleaseDate.text = "Release Date :" + movies[position].releaseDate
        holder.binding.tvOverview.text = movies[position].overview


        holder.binding.cvMovie.setOnClickListener(View.OnClickListener {
            var intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("MovieID", movieId.toString())
            context.startActivity(intent)
        })

        DownloadImageTask(holder.binding.ivMovieBanner).execute(Constants.BASE_IMAGE_URL + movies[position].posterPath);

    }


    class ViewHolder(var binding: LayoutMoviesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


}