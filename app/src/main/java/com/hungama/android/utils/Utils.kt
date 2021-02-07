package com.hungama.android.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import java.io.OutputStream

class Utils {
    companion object {
        fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
            // Check the status of the network connection.
            val connectivityManager =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun CopyStream(`is`: InputStream, os: OutputStream) {
            val buffer_size = 1024
            try {
                val bytes = ByteArray(buffer_size)
                while (true) {
                    val count: Int = `is`.read(bytes, 0, buffer_size)
                    if (count == -1) break
                    os.write(bytes, 0, count)
                }
            } catch (ex: Exception) {
            }
        }
    }


}