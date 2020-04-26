package com.theapache64.topcorn.di.modules

import android.content.SharedPreferences
import com.theapache64.topcorn.data.local.daos.MoviesDao
import com.theapache64.topcorn.data.remote.ApiInterface
import com.theapache64.topcorn.data.repositories.movies.MoviesRepo
import com.theapache64.topcorn.utils.test.OpenForTesting
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@OpenForTesting
@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideMoviesRepo(
        sharedPref: SharedPreferences,
        apiInterface: ApiInterface,
        moviesDao: MoviesDao
    ): MoviesRepo {
        return MoviesRepo(sharedPref, apiInterface, moviesDao)
    }

}