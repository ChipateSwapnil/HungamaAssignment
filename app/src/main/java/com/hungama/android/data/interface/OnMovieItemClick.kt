package com.hungama.android.data.`interface`

import com.hungama.android.data.model.Movies

open interface OnMovieItemClick {
    public fun onClick(movie: Movies.Result)
    public fun onMovieNameClick(position: Int)

}