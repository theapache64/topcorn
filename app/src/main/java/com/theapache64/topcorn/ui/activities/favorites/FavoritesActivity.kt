package com.theapache64.topcorn.ui.activities.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.theapache64.topcorn.R
import com.theapache64.topcorn.ui.activities.movie.MovieActivity
import com.theapache64.topcorn.ui.adapters.FavoritesAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_favorites.*
import javax.inject.Inject

class FavoritesActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.favorites)
        }

        viewModel = ViewModelProvider(this, factory).get(FavoritesViewModel::class.java)

        viewModel.favoritesMovies.observe(this, Observer {
            favorites_recycler.adapter = FavoritesAdapter(it) { movie ->
                startActivity(MovieActivity.getStartIntent(this, movie))
            }
        })

        viewModel.isListEmpty.observe(this, Observer { empty ->
            empty_text.visibility = if (empty) VISIBLE else GONE
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}