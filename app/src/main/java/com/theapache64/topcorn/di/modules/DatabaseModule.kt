package com.theapache64.topcorn.di.modules

import android.content.Context
import androidx.room.Room
import com.theapache64.topcorn.data.local.AppDatabase
import com.theapache64.twinkill.di.modules.ContextModule
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "com.theapache64.topcorn_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
}