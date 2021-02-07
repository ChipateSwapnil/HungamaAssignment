package com.hungama.android.utils

import android.util.Log
import androidx.viewbinding.BuildConfig

class LogUtils {

    companion object {

        fun LOGD(tag: String?, message: String?) {
//            if (BuildConfig.DEBUG) {
                if (message != null) {
                    Log.d(tag, message)
                }
//            }
        }

        fun LOGV(tag: String?, message: String?) {
//            if (BuildConfig.DEBUG) {
                if (message != null) {
                    Log.v(tag, message)
                }
//            }
        }

        fun LOGI(tag: String?, message: String?) {
//            if (BuildConfig.DEBUG) {
                if (message != null) {
                    Log.i(tag, message)
                }
//            }
        }

        fun LOGW(tag: String?, message: String?) {
//            if (BuildConfig.DEBUG) {
                if (message != null) {
                    Log.w(tag, message)
                }
//            }
        }

        fun LOGE(tag: String?, message: String?) {
//            if (BuildConfig.DEBUG) {
                if (message != null) {
                    Log.e(tag, message)
                }
//            }
        }
    }
}