package com.theapache64.topcorn.di.modules

import androidx.lifecycle.ViewModel
import com.theapache64.topcorn.ui.activities.feed.FeedViewModel
import com.theapache64.topcorn.ui.activities.movie.MovieViewModel
import com.theapache64.topcorn.ui.activities.splash.SplashViewModel
import com.theapache64.twinkill.di.modules.BaseViewModelModule
import com.theapache64.twinkill.utils.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.time.ExperimentalTime


@Module(includes = [BaseViewModelModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @ExperimentalTime
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindMainViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel

}