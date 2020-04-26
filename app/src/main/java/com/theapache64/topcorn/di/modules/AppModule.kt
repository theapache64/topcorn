package com.theapache64.topcorn.di.modules

import android.app.Application
import com.theapache64.twinkill.di.modules.PreferenceModule

import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ViewModelModule::class,
        PreferenceModule::class,
        ActivitiesBuilderModule::class,
        RepoModule::class
    ]
)
class AppModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return this.application
    }

}