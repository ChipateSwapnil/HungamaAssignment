package com.hungama.android.data.loader

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.hungama.android.data.api.NetworkUtils
import com.hungama.android.data.model.MovieCredits

class MovieCreditsLoader(context: Context, var movieId: String?) :
    AsyncTaskLoader<MovieCredits>(context) {

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): MovieCredits? {
        return movieId?.let { NetworkUtils().getMovieCredits(it) }
    }
}