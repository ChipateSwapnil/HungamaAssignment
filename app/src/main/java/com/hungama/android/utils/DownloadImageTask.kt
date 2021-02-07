package com.hungama.android.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.InputStream
import java.net.URL

class DownloadImageTask(bmImage: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {
        var bmImage: ImageView = bmImage

        override fun onPostExecute(result: Bitmap?) {
            bmImage.setImageBitmap(result)
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            val urldisplay = params[0]
            var bmp: Bitmap? = null
            try {
                val inputStream: InputStream = URL(urldisplay).openStream()
                bmp = BitmapFactory.decodeStream(inputStream)
            } catch (e: java.lang.Exception) {
                LogUtils.LOGE("Error", e.message)
                e.printStackTrace()
            }
            return bmp
        }
    }