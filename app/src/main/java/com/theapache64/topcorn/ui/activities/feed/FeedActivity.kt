package com.theapache64.topcorn.ui.activities.feed


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.theapache64.topcorn.R
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.databinding.ActivityFeedBinding
import com.theapache64.topcorn.ui.activities.movie.MovieActivity
import com.theapache64.topcorn.ui.adapters.FeedAdapter
import com.theapache64.topcorn.ui.adapters.MoviesAdapter
import com.theapache64.twinkill.logger.info
import com.theapache64.twinkill.network.utils.Resource
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class FeedActivity : BaseAppCompatActivity(), FeedHandler {

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, FeedActivity::class.java).apply {
                // add data here
            }
        }
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val binding = bindContentView<ActivityFeedBinding>(R.layout.activity_feed)

        this.viewModel = ViewModelProvider(this, factory).get(FeedViewModel::class.java)
        this.viewModel.movies.observe(this, Observer {

            when (it.status) {

                Resource.Status.LOADING -> {
                    binding.lvFeed.showLoading("Fetching movies..")
                    binding.rvFeed.visibility = View.INVISIBLE
                }

                Resource.Status.SUCCESS -> {
                    binding.lvFeed.hideLoading()
                    binding.rvFeed.visibility = View.VISIBLE

                    val adapter = FeedAdapter(this, it.data!!, { movies ->
                        MoviesAdapter(this, movies) { position, poster, title ->
                            info("Movie clicked $position")
                            val movie = movies[position]
                            goToMovieActivity(movie, poster, title)
                        }.apply {
                            setHasStableIds(true)
                        }
                    }) { position ->
                        info("Feed item clicked $position")
                    }.apply {
                        setHasStableIds(true)
                    }

                    binding.rvFeed.adapter = adapter
                }

                Resource.Status.ERROR -> {
                    binding.lvFeed.showError(it.message!!)
                    binding.rvFeed.visibility = View.GONE
                }
            }
        })

        viewModel.darkMode.observe(this, Observer { isDarkMode ->

            val darkModeFlag = if (isDarkMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(darkModeFlag)
        })

        binding.viewModel = viewModel
        binding.handler = this
    }

    private fun goToMovieActivity(
        movie: Movie,
        mcvPoster: View,
        tvTitle: View
    ) {
        val transition = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair(tvTitle, "title"),
            Pair(mcvPoster, "poster")
        )
        startActivity(MovieActivity.getStartIntent(this, movie), transition.toBundle())
    }

    override fun onToggleDarkModeClicked() {
        viewModel.toggleDarkMode()
    }

    override fun onHeartClicked() {
        // goto github page ;)
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://github.com/theapache64/topcorn")
        )
        startActivity(intent)
    }

}
