package com.theapache64.topcorn.ui.activities.splash


import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.theapache64.topcorn.R
import com.theapache64.topcorn.databinding.ActivitySplashBinding
import com.theapache64.topcorn.ui.activities.feed.FeedActivity
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : BaseAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding = bindContentView<ActivitySplashBinding>(R.layout.activity_splash)
        val viewModel = ViewModelProvider(this, factory).get(SplashViewModel::class.java)
        binding.viewModel = viewModel

        // Watching activity launch command
        viewModel.getLaunchActivityEvent().observe(this, Observer { activityName ->

            when (activityName) {

                FeedActivity::class.simpleName -> {
                    startActivity(FeedActivity.getStartIntent(this))
                }

                else -> throw IllegalArgumentException("Undefined activity id $activityName")
            }

            finish()

        })

        // Starting splash timer. This is just to show splash screen for a second.
        // Can be removed if you want don't want to see the splash.
        Handler().postDelayed({
            viewModel.goToNextScreen()
        }, SPLASH_DURATION)

    }


    companion object {
        private const val SPLASH_DURATION = 1000L
    }

}
