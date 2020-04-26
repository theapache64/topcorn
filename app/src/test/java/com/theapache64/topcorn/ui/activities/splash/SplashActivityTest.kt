package com.theapache64.topcorn.ui.activities.splash

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.theapache64.topcorn.R
import com.theapache64.topcorn.utils.test.waitFor
import com.theapache64.topcorn.ui.activities.feed.FeedActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {


    @Test
    fun goToFeedAfterDelay() = runBlocking {
        // given : Creating input data with when
        Intents.init()
        val sc = ActivityScenario.launch(SplashActivity::class.java)
        // Both logo and version number displayed
        onView(withId(R.id.iv_logo)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_version_name)).check(matches(isDisplayed()))

        // Checking if moved to feed
        onView(isRoot()).perform(waitFor(SplashViewModel.SPLASH_DURATION))
        intended(hasComponent(FeedActivity::class.java.name))

        Intents.release()
        sc.close()
    }
}
