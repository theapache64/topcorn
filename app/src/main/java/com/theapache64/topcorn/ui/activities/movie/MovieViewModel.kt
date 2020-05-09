package com.theapache64.topcorn.ui.activities.movie

import androidx.lifecycle.ViewModel
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class MovieViewModel @Inject constructor() : ViewModel() {

    fun init(movie: Movie) {
        this.movie = movie
    }

    val openImdb = SingleLiveEvent<Boolean>()
    val closeActivity = SingleLiveEvent<Boolean>()
    var movie: Movie? = null

    fun onBackButtonClicked() {
        closeActivity.value = true
    }

    fun onGoToImdbClicked() {
        openImdb.value = true
    }
}