package com.theapache64.topcorn.di.modules


import com.theapache64.topcorn.ui.activities.feed.FeedActivity
import com.theapache64.topcorn.ui.activities.movie.MovieActivity
import com.theapache64.topcorn.ui.activities.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * To hold activities to support AndroidInjection call from dagger.
 */
@Module
abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector
    abstract fun getSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun getMainActivity(): FeedActivity

    @ContributesAndroidInjector
    abstract fun getMovieActivity(): MovieActivity

}