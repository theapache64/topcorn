package com.theapache64.topcorn.ui.activities.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.theapache64.topcorn.R
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

        viewModel = ViewModelProvider(this, factory).get(FavoritesViewModel::class.java)

        viewModel.favoritesMovies.observe(this, Observer {
            favorites_recycler.adapter = FavoritesAdapter(it)
        })
    }
}