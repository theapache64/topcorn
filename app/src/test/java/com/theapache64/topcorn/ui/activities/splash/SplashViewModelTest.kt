package com.theapache64.topcorn.ui.activities.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.theapache64.topcorn.testutils.MainCoroutineRule
import com.theapache64.topcorn.testutils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @get:Rule
    val coroutinesRule = MainCoroutineRule()

    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun init() {
        splashViewModel = SplashViewModel(coroutinesRule.coroutineContext)
    }

    @Test
    fun navigation_goToNextScreen_liveDataUpdates() {
        // when : Calling subject -> navigation
        val x = splashViewModel.launchActivityEvent.getOrAwaitValue()
    }
}