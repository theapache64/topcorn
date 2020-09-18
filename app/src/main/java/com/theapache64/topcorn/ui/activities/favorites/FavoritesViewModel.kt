package com.theapache64.topcorn.ui.activities.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.topcorn.data.local.FavoriteMovie
import com.theapache64.topcorn.data.repositories.movies.MoviesRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {

    val favoritesMovies = MutableLiveData<List<FavoriteMovie>>()
    val isListEmpty = MutableLiveData<Boolean>()

    fun getFavorites() {
        viewModelScope.launch {
            val favorites = moviesRepo.getAllFavoriteMovies()
            favoritesMovies.postValue(favorites)
            isListEmpty.value = favorites.isNullOrEmpty()
        }
    }
}