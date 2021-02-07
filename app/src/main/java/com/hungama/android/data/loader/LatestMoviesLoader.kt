package com.hungama.android.data.loader

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.hungama.android.data.api.NetworkUtils
import com.hungama.android.data.model.Movies

class LatestMoviesLoader(context  : Context) : AsyncTaskLoader<Movies>(context) {

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }
    override fun loadInBackground(): Movies? {
        return NetworkUtils().getLatestMovies()
    }
}