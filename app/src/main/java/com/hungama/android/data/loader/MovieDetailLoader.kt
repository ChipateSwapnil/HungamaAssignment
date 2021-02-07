package com.hungama.android.data.loader

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.hungama.android.data.api.NetworkUtils
import com.hungama.android.data.model.MovieDetails

class MovieDetailLoader(context: Context, var movieId: String?) :
    AsyncTaskLoader<MovieDetails>(context) {

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): MovieDetails? {
        return movieId?.let { NetworkUtils().getMovieDetails(it) }
    }
}