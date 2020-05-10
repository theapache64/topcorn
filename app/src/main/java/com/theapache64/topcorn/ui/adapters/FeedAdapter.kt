package com.theapache64.topcorn.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.databinding.ItemFeedBinding
import com.theapache64.topcorn.models.FeedItem

class FeedAdapter(
    private val onViewClicked: (position: Movie, poster: MaterialCardView, title: TextView) -> Unit
) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    var feedItems = mutableListOf<FeedItem>()
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemFeedBinding.inflate(inflater!!, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = feedItems.size

    override fun getItemId(position: Int): Long {
        return feedItems[position].id
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed = feedItems[position]
        holder.binding.feed = feed
        val adapter = getMoviesAdapter(feed.movies)
        holder.binding.rvMovies.adapter = adapter
    }

    private fun getMoviesAdapter(movies: List<Movie>): MoviesAdapter {
        return MoviesAdapter(movies, onViewClicked).apply {
            stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    fun updateData(data: List<FeedItem>) {
        feedItems.clear()
        feedItems.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root)
}