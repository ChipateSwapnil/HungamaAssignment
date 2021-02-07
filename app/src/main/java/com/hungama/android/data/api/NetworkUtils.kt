package com.hungama.android.data.api

import android.net.Uri
import com.google.gson.Gson
import com.hungama.android.data.model.*
import com.hungama.android.utils.LogUtils
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class NetworkUtils {

    //https://api.themoviedb.org/3/movie/now_playing?api_key=1806dce5898dfe563b0ca07ceb848a91&language=en-US&page=1

    private lateinit var urlConnection: HttpsURLConnection
    private lateinit var reader: BufferedReader
    private lateinit var requestURL: URL
    private lateinit var builtURI: Uri
    private lateinit var inputStream: BufferedInputStream
    private lateinit var builder: StringBuilder
    private lateinit var jsonResponse: String


    fun getLatestMovies(): Movies {
        try {
            //build the full query URI for latest movie with the api key, language and page number
            //https://api.themoviedb.org/3/movie/now_playing?api_key=1806dce5898dfe563b0ca07ceb848a91&language=en-US&page=1
            builtURI = Uri.parse(BASE_URL + LATEST_MOVIE_URL).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .appendQueryParameter(LANGUAGE, LANGUAGE_VALUE)
                .appendQueryParameter(PAGE, 1.toString()).build()


            //open connection and get the inputStream
            openConnection(builtURI)

            //response from the stream
            jsonResponse = readStream(inputStream)

            LogUtils.LOGD("$TAG Latest Movie", jsonResponse)

            return Gson().fromJson(jsonResponse, Movies::class.java)
        } catch (e: Exception) {
            return Movies()
        }
    }

    fun getMovieDetails(movieId: String): MovieDetails {
        try {
            //build the full query URI for latest movie with the api key, language and page number
            //https://api.themoviedb.org/3/movie/now_playing?api_key=1806dce5898dfe563b0ca07ceb848a91&language=en-US&page=1
            builtURI = Uri.parse(BASE_URL + movieId).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .appendQueryParameter(LANGUAGE, LANGUAGE_VALUE).build()


            //open connection and get the inputStream
            openConnection(builtURI)

            //response from the stream
            jsonResponse = readStream(inputStream)

            LogUtils.LOGD("$TAG Movie Details", jsonResponse)

            return Gson().fromJson(jsonResponse, MovieDetails::class.java)
        } catch (e: Exception) {
            return MovieDetails()
        }
    }

    fun getMovieCredits(movieId: String): MovieCredits {
        try {
            //build the full query URI for latest movie with the api key, language and page number
            //https://api.themoviedb.org/3/movie/now_playing?api_key=1806dce5898dfe563b0ca07ceb848a91&language=en-US&page=1
            builtURI = Uri.parse(BASE_URL + movieId+ CREDIT).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .appendQueryParameter(LANGUAGE, LANGUAGE_VALUE).build()


            //open connection and get the inputStream
            openConnection(builtURI)

            //response from the stream
            jsonResponse = readStream(inputStream)

            LogUtils.LOGD("$TAG Movie Credits", jsonResponse)

            return Gson().fromJson(jsonResponse, MovieCredits::class.java)
        } catch (e: Exception) {
            return MovieCredits()
        }
    }

    fun getSimilarMovie(movieId: String): SimilarMovie {
        try {
            //build the full query URI for latest movie with the api key, language and page number
            //https://api.themoviedb.org/3/movie/now_playing?api_key=1806dce5898dfe563b0ca07ceb848a91&language=en-US&page=1
            builtURI = Uri.parse(BASE_URL + movieId+ SIMILAR_MOVIES).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .appendQueryParameter(LANGUAGE, LANGUAGE_VALUE).build()


            //open connection and get the inputStream
            openConnection(builtURI)

            //response from the stream
            jsonResponse = readStream(inputStream)

            LogUtils.LOGD("$TAG Similar Movie", jsonResponse)

            return Gson().fromJson(jsonResponse, SimilarMovie::class.java)
        } catch (e: Exception) {
            return SimilarMovie()
        }
    }

    fun getMovieVideos(movieId: String): MovieVideo {
        try {
            //build the full query URI for latest movie with the api key, language and page number
            //https://api.themoviedb.org/3/movie/now_playing?api_key=1806dce5898dfe563b0ca07ceb848a91&language=en-US&page=1
            builtURI = Uri.parse(BASE_URL + movieId+ VIDEOS).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)
                .appendQueryParameter(LANGUAGE, LANGUAGE_VALUE).build()


            //open connection and get the inputStream
            openConnection(builtURI)

            //response from the stream
            jsonResponse = readStream(inputStream)

            LogUtils.LOGD("$TAG Movie Videos", jsonResponse)

            return Gson().fromJson(jsonResponse, MovieVideo::class.java)
        } catch (e: Exception) {
            return MovieVideo()
        }
    }

    private fun openConnection(builtUri: Uri) {
        //cobverting URI to URL
        requestURL = URL(builtURI.toString())

        //Open the network connection
        urlConnection = requestURL.openConnection() as HttpsURLConnection
        urlConnection.requestMethod = "GET"
        urlConnection.connect()

        //Get the InputStream
        inputStream = BufferedInputStream(urlConnection.inputStream)
    }


    private fun readStream(inputStream: BufferedInputStream): String {
        //Create a buffered reader from that input stream.
        reader = BufferedReader(InputStreamReader(inputStream))

        //use a string builder to hold incoming response
        builder = StringBuilder()

        reader.forEachLine { builder.append(it) }

        return builder.toString()
    }

    companion object {
        const val TAG = "NetworkUtilsT"
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val LATEST_MOVIE_URL = "now_playing"
        const val CREDIT = "/credits"
        const val SIMILAR_MOVIES = "/similar"
        const val VIDEOS = "/videos"
        const val LANGUAGE = "language"
        const val API_KEY = "api_key"
        const val API_KEY_VALUE = "1806dce5898dfe563b0ca07ceb848a91"
        const val PAGE = "page"
        const val LANGUAGE_VALUE = "en-US"
    }

}