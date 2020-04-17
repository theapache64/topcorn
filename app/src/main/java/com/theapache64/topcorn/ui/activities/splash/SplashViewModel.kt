package com.theapache64.topcorn.ui.activities.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theapache64.topcorn.BuildConfig
import com.theapache64.topcorn.ui.activities.feed.FeedActivity
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(

) : ViewModel() {

    val versionName = "v${BuildConfig.VERSION_NAME}"

    private val launchActivityEvent = SingleLiveEvent<String>()

    fun getLaunchActivityEvent(): LiveData<String> {
        return launchActivityEvent
    }

    fun goToNextScreen() {

        val activityName = FeedActivity::class.simpleName

        // passing id with the finish notification
        launchActivityEvent.value = activityName
    }

    companion object {
        val TAG = SplashViewModel::class.java.simpleName
    }

}