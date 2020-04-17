package com.theapache64.topcorn.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.theapache64.topcorn.data.remote.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Insert
    fun addAll(data: List<Movie>)
}
