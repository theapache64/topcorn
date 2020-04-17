package com.theapache64.topcorn.ui.activities.feed


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.data.repositories.MoviesRepo
import com.theapache64.topcorn.models.FeedItem
import com.theapache64.twinkill.network.utils.Resource
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class FeedViewModel @Inject constructor(
    moviesRepo: MoviesRepo
) : ViewModel() {

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    val movies: LiveData<Resource<List<FeedItem>>> = moviesRepo
        .getTop250Movies()
        .map {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Resource.loading()
                }
                Resource.Status.SUCCESS -> {
                    val movies = it.data!!
                    val feedItems = convertToFeed(movies)
                    Resource.success(feedItems)
                }

                Resource.Status.ERROR -> {
                    Resource.error(it.message!!)
                }
            }
        }
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.Default)

    private fun convertToFeed(movies: List<Movie>): List<FeedItem> {

        val genreSet = mutableSetOf<String>()
        for (movie in movies) {
            for (genre in movie.genre) {
                genreSet.add(genre)
            }
        }
        val feedItems = mutableListOf<FeedItem>()
        for ((index, genre) in genreSet.withIndex()) {
            val genreMovies = movies
                .filter { it.genre.contains(genre) }
            feedItems.add(FeedItem(index.toLong(), genre, genreMovies.shuffled()))
        }
        return feedItems
    }

    private val _darkMode = SingleLiveEvent<Boolean>()
    val darkMode: LiveData<Boolean> = _darkMode

    fun toggleDarkMode() {
        val newDarkMode = !(darkMode.value ?: false)
        _darkMode.value = newDarkMode
    }
}