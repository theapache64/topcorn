package com.theapache64.topcorn.ui.activities.feed


import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.theapache64.topcorn.R
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

        const val SORT_ORDER_YEAR = 1
        private const val SORT_ORDER_RATING = 2

        /**
         * To convert movie list to categorized feed
         */
        fun convertToFeed(movies: List<Movie>, sortOrder: Int): List<FeedItem> {

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
                    .sortedByDescending {
                        when (sortOrder) {
                            SORT_ORDER_RATING -> it.rating
                            SORT_ORDER_YEAR -> it.year?.toFloat() ?: 0f
                            else -> {
                                throw IllegalArgumentException("TSH : sort order '$sortOrder' not managed")
                            }
                        }
                    }

                feedItems.add(
                    FeedItem(
                        index.toLong(),
                        genre,
                        genreMovies
                    )
                )
            }
            return feedItems
        }
    }

    private val _toast = MutableLiveData<Int>()
    val toast: LiveData<Int> = _toast

    val openGithub = SingleLiveEvent<Boolean>()

    private val sortedOrder = MutableLiveData<Int>()

    init {
        sortedOrder.value = SORT_ORDER_YEAR
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    val movies: LiveData<Resource<List<FeedItem>>> = sortedOrder.switchMap { sortOrder ->
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
                        val feedItems = convertToFeed(movies, sortOrder)
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

    /*
     * Sort order
     */
    val sortOrderIcon = ObservableInt(R.drawable.ic_star)

    fun onToggleSortOrderClicked() {

        val newSortOrderIcon = if (sortOrderIcon.get() == R.drawable.ic_calendar) {
            // change to stars
            R.drawable.ic_star
        } else {
            R.drawable.ic_calendar
        }

        when (newSortOrderIcon) {

            R.drawable.ic_star -> {
                // load movies by year
                sortedOrder.value = SORT_ORDER_YEAR
                _toast.value = R.string.feed_toast_sort_year
            }

            R.drawable.ic_calendar -> {
                // load movies by star
                sortedOrder.value = SORT_ORDER_RATING
                _toast.value = R.string.feed_toast_sort_rating
            }

            else -> throw IllegalArgumentException("TSH : $newSortOrderIcon sort order not managed")
        }

        sortOrderIcon.set(newSortOrderIcon)
    }
}