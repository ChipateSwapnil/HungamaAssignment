package com.hungama.android.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.hungama.android.adapter.MoviesListAdapter
import com.hungama.android.data.loader.LatestMoviesLoader
import com.hungama.android.data.model.Movies
import com.hungama.android.databinding.ActivityMovieListingBinding
import com.hungama.android.utils.Utils
import java.io.Serializable


class MovieListingActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Movies> {

    lateinit var binding: ActivityMovieListingBinding
    private var moviesListAdapter: MoviesListAdapter
    private var context: Context = this
    private val LOADER_ID = 1
    private var movies: ArrayList<Movies.Result> = ArrayList()
    private lateinit var loaderManager: LoaderManager

    init {
        moviesListAdapter = MoviesListAdapter(movies, context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        getMovieListing()
    }


    private fun setView() {
        binding.rvLatestMovies.layoutManager = LinearLayoutManager(context)
        binding.rvLatestMovies.adapter = moviesListAdapter

        binding.toolbar.ivSearch.setOnClickListener(View.OnClickListener {
            //on search click pass the loaded movies to Search Screen
            val intent = Intent(context, SearchActivity::class.java)
            val args = Bundle()
            args.putSerializable("ARRAYLIST", movies as Serializable?)
            intent.putExtra("BUNDLE", args)
            startActivity(intent)
        })
    }

    private fun getMovieListing() {
        if (Utils.verifyAvailableNetwork(this)) {
            //Calling the api using loader
            LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this)
        } else {
            Toast.makeText(context, "Please connect to internet", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Movies> {
        return LatestMoviesLoader(this)
    }


    override fun onLoadFinished(loader: Loader<Movies>, data: Movies?) {
        try {
            //result fetched from callback
            if (data?.results != null && data.results.isNotEmpty()) {
                movies = data.results as ArrayList<Movies.Result>
                moviesListAdapter.setAdapter(movies)
                binding.toolbar.ivSearch.visibility = View.VISIBLE
            } else {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                binding.toolbar.ivSearch.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
            binding.toolbar.ivSearch.visibility = View.VISIBLE
        }
    }

    override fun onLoaderReset(loader: Loader<Movies>) {
        TODO("Not yet implemented")
    }

}