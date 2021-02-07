package com.hungama.android.data.loader

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.hungama.android.data.api.NetworkUtils
import com.hungama.android.data.model.MovieVideo

class MovieVideoLoader(context: Context, var movieId: String?) :
    AsyncTaskLoader<MovieVideo>(context) {

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): MovieVideo? {
        return movieId?.let { NetworkUtils().getMovieVideos(it) }
    }
}