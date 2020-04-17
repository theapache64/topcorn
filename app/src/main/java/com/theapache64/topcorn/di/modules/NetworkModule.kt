package com.theapache64.topcorn.di.modules

import com.theapache64.topcorn.data.remote.ApiInterface
import com.theapache64.twinkill.network.di.modules.BaseNetworkModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * To hold all network related objects.
 */
@Module(includes = [BaseNetworkModule::class])
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}