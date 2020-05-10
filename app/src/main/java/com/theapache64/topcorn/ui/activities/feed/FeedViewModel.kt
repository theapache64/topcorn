package com.theapache64.topcorn.ui.activities.feed


import androidx.lifecycle.*
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.data.repositories.movies.MoviesRepo
import com.theapache64.topcorn.models.FeedItem
import com.theapache64.topcorn.utils.test.EspressoIdlingResource
import com.theapache64.twinkill.network.utils.Resource
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.time.ExperimentalTime


class FeedViewModel @Inject constructor(
    moviesRepo: MoviesRepo
) : ViewModel() {

    companion object {
        fun convertToFeed(movies: List<Movie>): List<FeedItem> {

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
                feedItems.add(
                    FeedItem(
                        index.toLong(),
                        genre,
                        genreMovies.sortedByDescending { it.rating })
                )
            }
            return feedItems
        }
    }

    val openGithub = SingleLiveEvent<Boolean>()

    private val loadMovies = MutableLiveData<Boolean>()

    init {
        loadMovies.value = true
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    val movies: LiveData<Resource<List<FeedItem>>> = loadMovies.switchMap {
        moviesRepo
            .getTop250Movies()
            .map {
                when (it.status) {

                    Resource.Status.LOADING -> {
                        EspressoIdlingResource.increment()
                        Resource.loading()
                    }

                    Resource.Status.SUCCESS -> {
                        val movies = it.data!!
                        val feedItems = convertToFeed(movies)
                        EspressoIdlingResource.decrement()
                        Resource.success(feedItems)
                    }

                    Resource.Status.ERROR -> {
                        EspressoIdlingResource.decrement()
                        Resource.error(it.message!!)
                    }
                }
            }
            .asLiveData(viewModelScope.coroutineContext)
    }

    private val _darkMode = SingleLiveEvent<Boolean>()
    val darkMode: LiveData<Boolean> = _darkMode

    fun onToggleDarkModeClicked() {
        val newDarkMode = !(darkMode.value ?: false)
        _darkMode.value = newDarkMode
    }

    fun onHeartClicked() {
        openGithub.value = true
    }
}