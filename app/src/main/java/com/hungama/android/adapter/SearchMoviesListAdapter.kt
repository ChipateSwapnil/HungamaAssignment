package com.hungama.android.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.hungama.android.data.`interface`.OnMovieItemClick
import com.hungama.android.data.model.Movies
import com.hungama.android.databinding.LayoutMoviesListItemBinding
import com.hungama.android.utils.Constants
import com.hungama.android.utils.DownloadImageTask
import com.hungama.android.utils.LogUtils
import com.hungama.android.view.MovieDetailActivity


class SearchMoviesListAdapter(
    var movies: ArrayList<Movies.Result>,
    var context: Context,
    var onMovieClick: OnMovieItemClick
) :
    RecyclerView.Adapter<SearchMoviesListAdapter.ViewHolder>(), Filterable {

    var movieFilterList = ArrayList<Movies.Result>()

    init {
        movieFilterList = movies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutMoviesListItemBinding = LayoutMoviesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setAdapter(movies: ArrayList<Movies.Result>) {
        this.movieFilterList = movies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movieFilterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var movieId = movieFilterList[position].id
        holder.binding.tvMovieTitle.text = movieFilterList[position].title
        holder.binding.tvReleaseDate.text = "Release Date :" + movieFilterList[position].releaseDate
        holder.binding.tvOverview.text = movieFilterList[position].overview
        DownloadImageTask(holder.binding.ivMovieBanner).execute(Constants.BASE_IMAGE_URL + movieFilterList[position].posterPath);


        holder.binding.cvMovie.setOnClickListener(View.OnClickListener {
            onMovieClick.onClick(movieFilterList[position])
            var intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("MovieID", movieId.toString())
            context.startActivity(intent)
        })


    }


    class ViewHolder(var binding: LayoutMoviesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    movieFilterList = movies
                } else {
                    var resultList = ArrayList<Movies.Result>()
                    resultList.addAll(search(movies, charSearch) as ArrayList<Movies.Result>)
                    movieFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = movieFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movieFilterList = results?.values as ArrayList<Movies.Result>
                notifyDataSetChanged()
            }

        }
    }

    private fun search(
        movieData: ArrayList<Movies.Result>,
        startChar: String
    ): List<Movies.Result> {
        var listData: MutableList<Movies.Result> = emptyList<Movies.Result>().toMutableList();

        var l = 0
        var r = movieData.size - 1
        while (l <= r) {
            //val m = l
            val resArr = movieData[l].title?.split(" ")?.toTypedArray()
            if (resArr != null) {
                for (i in resArr.indices) {
                    if (resArr[i]?.startsWith(startChar, true)) {
                        LogUtils.LOGD("MoviesTAG", movieData[l].title)
                        LogUtils.LOGD("startCharTAG", resArr[i])
                        listData.add(movieData[l])
                        break
                    }
                }
                l++
            }
        }

        LogUtils.LOGD("ResultTag", listData.toString())
        return listData;
    }


}