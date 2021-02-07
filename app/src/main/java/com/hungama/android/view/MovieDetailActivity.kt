package com.hungama.android.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.app.LoaderManager.LoaderCallbacks
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.hungama.android.adapter.MovieCastAdapter
import com.hungama.android.adapter.MovieCrewAdapter
import com.hungama.android.adapter.SimilarMoviesAdapter
import com.hungama.android.data.loader.MovieCreditsLoader
import com.hungama.android.data.loader.MovieDetailLoader
import com.hungama.android.data.loader.SimilarMovieLoader
import com.hungama.android.data.model.MovieCredits
import com.hungama.android.data.model.MovieDetails
import com.hungama.android.data.model.SimilarMovie
import com.hungama.android.databinding.ActivityMovieDetailBinding
import com.hungama.android.utils.Constants
import com.hungama.android.utils.DownloadImageTask
import com.hungama.android.utils.Utils


class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailBinding
    private var context: Context = this
    private var movieId: String = ""

    //Loader id for calling the AsyncTaskLoaders
    private val LOADER_MOVIE_DETAIL_ID = 2
    private val LOADER_MOVIE_CREDITS_ID = 3
    private val LOADER_SIMILAR_MOVIES_ID = 4
    private val LOADER_MOVIE_VIDEOS_ID = 5

    //init list for casts,crews,similar movies and movie videos
    private var movieCasts: ArrayList<MovieCredits.Cast> = ArrayList()
    private var movieCrews: ArrayList<MovieCredits.Crew> = ArrayList()
    private var similarMovies: ArrayList<SimilarMovie.Result> = ArrayList()
//    private var movieVideos: ArrayList<MovieVideo.Result> = ArrayList()

    //adapters for casts,crews,similar movies and movie videos
    private var movieCastAdapter: MovieCastAdapter
    private var movieCrewAdapter: MovieCrewAdapter
    private var similarMoviesAdapter: SimilarMoviesAdapter
//    private var movieVideoAdapter: MovieVideoAdapter


    init {
        movieCastAdapter = MovieCastAdapter(movieCasts, context)
        movieCrewAdapter = MovieCrewAdapter(movieCrews, context)
        similarMoviesAdapter = SimilarMoviesAdapter(similarMovies, context)
//        movieVideoAdapter = MovieVideoAdapter(movieVideos, context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        setView()

        if (movieId.trim().isNotEmpty()) {
            //call movieId if is not empty
            getMovieDetails()
            getMovieCredits()
            getSimilarMovies()
//            getMovieVideos()
        } else {
            Toast.makeText(context, "Please check the movie Id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getIntentData() {
        if (intent.hasExtra("MovieID"))
            movieId = intent.getStringExtra("MovieID").toString()
    }

    private fun setView() {
        //set the RecyclerView for Cast
        binding.rvMovieCast.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieCast.adapter = movieCastAdapter

        //set the RecyclerView for Crew
        binding.rvMovieCrew.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieCrew.adapter = movieCrewAdapter

        //set the RecyclerView for Similar Movies
        binding.rvSimilarMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSimilarMovie.adapter = similarMoviesAdapter

//        //set the RecyclerView for Movie Videos
//        binding.rvMovieVideo.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvMovieVideo.adapter = movieVideoAdapter
    }

    private fun getMovieDetails() {
        if (Utils.verifyAvailableNetwork(this)) {
            val queryBundle = Bundle()
            queryBundle.putString("movieId", movieId)
            LoaderManager.getInstance(this)
                .initLoader(LOADER_MOVIE_DETAIL_ID, queryBundle, dataResultMovieDetailLoader)
        } else {
            Toast.makeText(context, "Please connect to internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMovieCredits() {
        if (Utils.verifyAvailableNetwork(this)) {
            val queryBundle = Bundle()
            queryBundle.putString("movieId", movieId)

            LoaderManager.getInstance(this)
                .initLoader(LOADER_MOVIE_CREDITS_ID, queryBundle, dataResultMovieCreditsLoader)
        } else {
            Toast.makeText(context, "Please connect to internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSimilarMovies() {
        if (Utils.verifyAvailableNetwork(this)) {
            val queryBundle = Bundle()
            queryBundle.putString("movieId", movieId)
            LoaderManager.getInstance(this)
                .initLoader(LOADER_SIMILAR_MOVIES_ID, queryBundle, dataResultSimilarMoviesLoader)
        } else {
            Toast.makeText(context, "Please connect to internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMovieVideos() {
//        if (Utils.verifyAvailableNetwork(this)) {
//            val queryBundle = Bundle()
//            queryBundle.putString("movieId", movieId)
//            LoaderManager.getInstance(this)
//                .initLoader(LOADER_MOVIE_VIDEOS_ID, queryBundle, dataResultMovieVideosLoader)
//        } else {
//            Toast.makeText(context, "Please connect to internet", Toast.LENGTH_SHORT).show()
//        }
    }


    private var dataResultMovieDetailLoader = object : LoaderCallbacks<MovieDetails> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<MovieDetails> {
            var moveId: String? = ""

            if (args != null) {
                moveId = args.getString("movieId")
            }
            return MovieDetailLoader(context, moveId)
        }

        override fun onLoadFinished(loader: Loader<MovieDetails>, data: MovieDetails?) {
            try {
                if (data != null) {
                    binding.tvMovieTitle.text = data.title
                    binding.tvReleaseDate.text = "Release Date : ${data.releaseDate}"
                    binding.tvLanguage.text = "Language : ${data.originalLanguage}"
                    binding.tvOverview.text = data.overview
                    DownloadImageTask(binding.ivMovieBanner).execute(Constants.BASE_IMAGE_URL + data.posterPath);
                    DownloadImageTask(binding.ivMovieBannerBackDrop).execute(Constants.BASE_IMAGE_URL + data.backdropPath);
                } else {
                    Toast.makeText(context, "Movie Detail not Found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load movie Detail", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onLoaderReset(loader: Loader<MovieDetails>) {
            TODO("Not yet implemented")
        }
    }

    private var dataResultMovieCreditsLoader = object : LoaderCallbacks<MovieCredits> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<MovieCredits> {
            var moveId: String? = ""

            if (args != null) {
                moveId = args.getString("movieId")
            }

            return MovieCreditsLoader(context, moveId)

        }

        override fun onLoadFinished(loader: Loader<MovieCredits>, data: MovieCredits?) {
            try {
                if (data?.cast != null && data.cast!!.isNotEmpty()) {
                    movieCasts = data.cast as ArrayList<MovieCredits.Cast>
                    movieCastAdapter.setAdapter(movieCasts)
                } else {
                    Toast.makeText(context, "Movie Cast Not found", Toast.LENGTH_SHORT).show()
                }

                if (data?.crew != null && data.crew!!.isNotEmpty()) {
                    movieCrews = data.crew as ArrayList<MovieCredits.Crew>
                    movieCrewAdapter.setAdapter(movieCrews)
                } else {
                    Toast.makeText(context, "Movie Cast Not found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load movie cast", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onLoaderReset(loader: Loader<MovieCredits>) {
            TODO("Not yet implemented")
        }
    }

    private var dataResultSimilarMoviesLoader = object : LoaderCallbacks<SimilarMovie> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<SimilarMovie> {
            var moveId: String? = ""

            if (args != null) {
                moveId = args.getString("movieId")
            }
            return SimilarMovieLoader(context, moveId)
        }

        override fun onLoadFinished(loader: Loader<SimilarMovie>, data: SimilarMovie?) {
            try {
                if (data?.results != null && data.results!!.isNotEmpty()) {
                    similarMovies = data.results as ArrayList<SimilarMovie.Result>
                    similarMoviesAdapter.setAdapter(similarMovies)
                } else {
                    Toast.makeText(context, "Similar Movies not found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load similar movies", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onLoaderReset(loader: Loader<SimilarMovie>) {
            TODO("Not yet implemented")
        }
    }


//    private var dataResultMovieVideosLoader = object : LoaderCallbacks<MovieVideo> {
//        override fun onCreateLoader(id: Int, args: Bundle?): Loader<MovieVideo> {
//            var moveId: String? = ""
//
//            if (args != null) {
//                moveId = args.getString("movieId")
//            }
//            return MovieVideoLoader(context, moveId)
//        }
//
//        override fun onLoadFinished(loader: Loader<MovieVideo>, data: MovieVideo?) {
//            try {
//                if (data?.results != null && data.results!!.isNotEmpty()) {
//                    movieVideos = data.results as ArrayList<MovieVideo.Result>
//                    movieVideoAdapter.setAdapter(movieVideos)
//                } else {
//                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        override fun onLoaderReset(loader: Loader<MovieVideo>) {
//            TODO("Not yet implemented")
//        }
//    }
}