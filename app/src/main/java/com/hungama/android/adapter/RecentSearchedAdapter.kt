package com.hungama.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hungama.android.data.`interface`.OnMovieItemClick
import com.hungama.android.databinding.LayoutRecentSearchedItemBinding

class RecentSearchedAdapter(var searchedList: ArrayList<String>, var context: Context,var onMovieItemClick: OnMovieItemClick) :
    RecyclerView.Adapter<RecentSearchedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentSearchedAdapter.ViewHolder {
        val binding: LayoutRecentSearchedItemBinding = LayoutRecentSearchedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setAdapter(searchedList: ArrayList<String>) {
        this.searchedList = searchedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return searchedList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvRecentSearchMovieTitle.text = searchedList[position]

        holder.binding.cvRecentSearchedMovie.setOnClickListener( View.OnClickListener {
            onMovieItemClick.onMovieNameClick(position)
        })
    }

    class ViewHolder(var binding: LayoutRecentSearchedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}