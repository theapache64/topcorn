package com.theapache64.topcorn


import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.theapache64.topcorn.di.components.DaggerAppComponent
import com.theapache64.topcorn.utils.retrofit.FlowResourceCallAdapterFactory
import com.theapache64.twinkill.TwinKill
import com.theapache64.twinkill.di.modules.ContextModule
import com.theapache64.twinkill.googlesans.GoogleSans
import com.theapache64.twinkill.network.di.modules.BaseNetworkModule
import com.theapache64.twinkill.network.utils.retrofit.interceptors.CurlInterceptor
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/theapache64/top250/master/"
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>


    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Dagger
        DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .baseNetworkModule(BaseNetworkModule(BASE_URL))
            .build().inject(this)

        // TwinKill
        TwinKill.init(
            TwinKill
                .builder()
                .addCallAdapter(FlowResourceCallAdapterFactory()) // to return Flow<Resource<T>> from retrofit
                .addOkHttpInterceptor(CurlInterceptor())
                .setDefaultFont(GoogleSans.Regular)
                .build()
        )

    }

}
