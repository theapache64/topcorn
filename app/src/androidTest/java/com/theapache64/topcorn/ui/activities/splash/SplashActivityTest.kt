@file:Suppress("IllegalIdentifier")

package com.theapache64.topcorn.ui.activities.splash

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @Test
    fun `Go to next screen after delay`() {

        // given : Creating input data with when
        val activityScenario = ActivityScenario.launch(SplashActivity::class.java)

        // when : Calling subject -> given
        TODO("Call subject here")

        // then : Asserting the condition -> then
        TODO("Add assertions here ")
    }
}