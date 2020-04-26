package com.theapache64.topcorn.ui.activities.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.theapache64.topcorn.utils.test.MainCoroutineRule
import com.theapache64.topcorn.utils.test.getOrAwaitValue
import com.theapache64.topcorn.ui.activities.feed.FeedActivity
import com.winterbe.expekt.should
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@FlowPreview
@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = MainCoroutineRule()

    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun init() {
        splashViewModel = SplashViewModel()
    }

    @Test
    fun `After splash, should go to feed`() {
        // when : Calling subject -> navigation
        val actualResult = splashViewModel.launchActivityEvent.getOrAwaitValue {
            coroutinesRule.advanceTimeBy(SplashViewModel.SPLASH_DURATION)
        }
        actualResult.should.equal(FeedActivity::class.simpleName)
    }

}