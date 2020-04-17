package com.theapache64.topcorn.ui.activities.movie

import androidx.lifecycle.ViewModel
import com.theapache64.topcorn.data.remote.Movie
import javax.inject.Inject

class MovieViewModel @Inject constructor() : ViewModel() {
    fun init(movie: Movie) {
        this.movie = movie
    }

    var movie: Movie? = null
}