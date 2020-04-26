package com.theapache64.topcorn.ui.activities.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.theapache64.topcorn.BuildConfig
import com.theapache64.topcorn.ui.activities.feed.FeedActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SplashViewModel @Inject constructor(
) : ViewModel() {

    companion object {
        val TAG = SplashViewModel::class.java.simpleName
        const val SPLASH_DURATION = 1_000L
    }

    val versionName = "v${BuildConfig.VERSION_NAME}"

    @ExperimentalCoroutinesApi
    @FlowPreview
    val launchActivityEvent = flowOf(FeedActivity::class.simpleName)
        .onStart {
            delay(SPLASH_DURATION)
        }
        .asLiveData()


}