package com.theapache64.topcorn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.theapache64.topcorn.data.local.daos.MoviesDao
import com.theapache64.topcorn.data.remote.Movie

@Database(entities = [Movie::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}