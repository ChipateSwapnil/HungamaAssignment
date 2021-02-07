package com.hungama.android.data.loader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.loader.content.AsyncTaskLoader
import com.hungama.android.utils.LogUtils
import java.io.InputStream
import java.net.URL


class DownloadImageLoader(context: Context, var imageURL: String) :
    AsyncTaskLoader<Bitmap>(context) {

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): Bitmap? {
        var bmp: Bitmap? = null
        try {
            val `in`: InputStream = URL(imageURL).openStream()
            bmp = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            LogUtils.LOGE("Error", e.message)
            e.printStackTrace()
        }
        return bmp
    }
}