package com.theapache64.topcorn.ui.activities.movie

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.theapache64.topcorn.R
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.databinding.ActivityMovieBinding
import com.theapache64.twinkill.ui.activities.base.BaseAppCompatActivity
import com.theapache64.twinkill.utils.extensions.bindContentView
import dagger.android.AndroidInjection
import javax.inject.Inject

class MovieActivity : BaseAppCompatActivity() {

    companion object {
        const val KEY_MOVIE = "movie"
        const val IMDB_DOT_COM = "https://imdb.com"
        fun getStartIntent(context: Context, movie: Movie): Intent {
            return Intent(context, MovieActivity::class.java).apply {
                // data goes here
                putExtra(KEY_MOVIE, movie)
            }
        }
    }


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMovieBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = bindContentView(R.layout.activity_movie)

        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        val movie = intent.getSerializableExtra(KEY_MOVIE) as Movie
        viewModel.init(movie)

        viewModel.closeActivity.observe(this, Observer {
            finish()
        })

        viewModel.openImdb.observe(this, Observer {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("$IMDB_DOT_COM${viewModel.movie!!.imdbUrl}")
            )
            startActivity(intent)
        })

        binding.viewModel = viewModel
    }


}
