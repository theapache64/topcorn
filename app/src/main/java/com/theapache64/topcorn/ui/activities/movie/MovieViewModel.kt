package com.theapache64.topcorn.ui.activities.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.topcorn.data.local.FavoriteMovie
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.data.repositories.movies.MoviesRepo
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    val isFavorite = MutableLiveData<Boolean>()

    fun init(movie: Movie) {
        this.movie = movie
        viewModelScope.launch {
            val favorites = moviesRepo.getAllFavoriteMovies()
            isFavorite.postValue(favorites.any { it.imageUrl == movie.imageUrl })
        }
    }

    val openImdb = SingleLiveEvent<Boolean>()
    val closeActivity = SingleLiveEvent<Boolean>()
    var movie: Movie? = null

    fun onBackButtonClicked() {
        closeActivity.value = true
    }

    fun onFavoriteButtonClicked() {
        viewModelScope.launch {
            when (isFavorite.value) {
                false -> {
                    moviesRepo.insertToFavoritesAsync(
                        FavoriteMovie(
                            movie?.actors,
                            movie?.desc,
                            movie?.directors,
                            movie?.genre,
                            movie?.imageUrl,
                            movie?.thumbUrl,
                            movie?.imdbUrl,
                            movie?.name,
                            movie?.rating,
                            movie?.year
                        )
                    )
                }
                true -> moviesRepo.deleteByUrlAsync(movie?.imageUrl ?: "")
            }
        }
        isFavorite.value = isFavorite.value != true
    }

    fun onGoToImdbClicked() {
        openImdb.value = true
    }
}