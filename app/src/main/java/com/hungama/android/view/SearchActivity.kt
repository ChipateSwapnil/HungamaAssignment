package com.hungama.android.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hungama.android.R
import com.hungama.android.adapter.RecentSearchedAdapter
import com.hungama.android.adapter.SearchMoviesListAdapter
import com.hungama.android.data.`interface`.OnMovieItemClick
import com.hungama.android.data.model.Movies
import com.hungama.android.databinding.ActivitySearchBinding
import java.lang.reflect.Type


class SearchActivity : AppCompatActivity(), OnMovieItemClick {

    lateinit var binding: ActivitySearchBinding
    private var context: Context = this
    private var list = arrayListOf<Movies.Result>()
    private var searchedValue = arrayListOf<String>()
    private var searchedList = arrayListOf<Movies.Result>()
    lateinit var adapter: SearchMoviesListAdapter
    lateinit var searchAdpter: RecentSearchedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        setView()
    }

    private fun getIntentData() {
        val intent = intent
        val args = intent.getBundleExtra("BUNDLE")
        list = (args!!.getSerializable("ARRAYLIST") as ArrayList<Movies.Result>?)!!

        searchedList = getSearchedValue()
        for (movie in searchedList) {
            movie.title?.let { searchedValue.add(it) }
        }
    }

    private fun setView() {

        //Searchview icons color
        val cancelIcon = binding.svMovieSearch.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)
        val searchIcon = binding.svMovieSearch.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val textView = binding.svMovieSearch.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        //set layout manager to recyclerview
        binding.rvMovieList.layoutManager = LinearLayoutManager(context)
        binding.rvMovieList.setHasFixedSize(true)

        //setting adapter to recyclerview
        adapter = SearchMoviesListAdapter(list, context, this)
        binding.rvMovieList.adapter = adapter

        binding.rvRecentSearched.layoutManager = LinearLayoutManager(context)
        binding.rvRecentSearched.setHasFixedSize(true)

        searchAdpter = RecentSearchedAdapter(searchedValue, context, this)
        binding.rvRecentSearched.adapter = searchAdpter

        hideMovieRV()


        //for searching the movies add Text listener to searchview
        binding.svMovieSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isNotEmpty()) {
                        showMovieRV()
                    } else {
                        hideMovieRV()
                    }
                }
                adapter.filter.filter(newText)
                return false
            }

        })

    }

    fun hideMovieRV() {
        binding.rvMovieList.visibility = View.GONE
        binding.tvRecentSearchLabel.visibility = View.VISIBLE
        binding.rvRecentSearched.visibility = View.VISIBLE
    }

    fun showMovieRV() {
        binding.rvMovieList.visibility = View.VISIBLE
        binding.tvRecentSearchLabel.visibility = View.GONE
        binding.rvRecentSearched.visibility = View.GONE
    }

    private fun storeRecentSearch() {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("Searched_Result", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        val json = gson.toJson(searchedList)

        editor.putString("searched_value", json)
        editor.apply()
    }

    private fun getSearchedValue(): ArrayList<Movies.Result> {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("Searched_Result", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("searched_value", "")
        val type: Type = object : TypeToken<List<Movies.Result?>?>() {}.type

        if (json != null && json.isNotEmpty()) {
            return gson.fromJson<List<Movies.Result>>(json, type) as ArrayList<Movies.Result>
        } else {
            return ArrayList<Movies.Result>()
        }
    }

    override fun onClick(movie: Movies.Result) {
        var movieName = movie.title
        if (movieName != null) {
            if (!(searchedValue.any { it.contentEquals(movieName) })) {
                if (searchedValue.size >= 5) {
                    searchedValue.removeAt(0)
                    searchedList.removeAt(0)
                    addSearchedValue(movie)
                } else {
                    addSearchedValue(movie)
                }
            }
        }
    }

    override fun onMovieNameClick(position: Int) {
        var intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("MovieID", searchedList[position].id.toString())
        context.startActivity(intent)
    }

    fun addSearchedValue(movie: Movies.Result) {
        movie.title?.let { searchedValue.add(it) }
        searchedList.add(movie)
        searchAdpter.setAdapter(searchedValue)
        storeRecentSearch()
    }


}