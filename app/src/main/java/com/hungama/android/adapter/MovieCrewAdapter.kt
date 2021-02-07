package com.hungama.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hungama.android.R
import com.hungama.android.data.model.MovieCredits
import com.hungama.android.databinding.LayoutMovieCastItemBinding
import com.hungama.android.utils.Constants
import com.hungama.android.utils.DownloadImageTask


class MovieCrewAdapter(var movieCrews: ArrayList<MovieCredits.Crew>, var context: Context) :
    RecyclerView.Adapter<MovieCrewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutMovieCastItemBinding = LayoutMovieCastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setAdapter(movieCrews: ArrayList<MovieCredits.Crew>) {
        this.movieCrews = movieCrews
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieCrews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCastOriginalName.text = movieCrews[position].originalName
        holder.binding.tvCastName.text = movieCrews[position].department
        var path = movieCrews[position].profilePath
        if (path != null)
            DownloadImageTask(holder.binding.ivMovieCast).execute(Constants.BASE_IMAGE_URL + path);
        else
            holder.binding.ivMovieCast.setImageDrawable(context.resources.getDrawable(R.drawable.img_default))
    }

    class ViewHolder(var binding: LayoutMovieCastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}