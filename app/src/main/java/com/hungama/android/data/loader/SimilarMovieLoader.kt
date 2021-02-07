package com.hungama.android.data.loader

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.hungama.android.data.api.NetworkUtils
import com.hungama.android.data.model.SimilarMovie

class SimilarMovieLoader(context: Context, var movieId: String?) :
    AsyncTaskLoader<SimilarMovie>(context) {

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): SimilarMovie? {
        return movieId?.let { NetworkUtils().getSimilarMovie(it) }
    }
}