package com.theapache64.topcorn.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.databinding.ItemFeedBinding
import com.theapache64.topcorn.models.FeedItem

class FeedAdapter(
    private val feedItems: List<FeedItem>,
    private val getMoviesAdapter: (List<Movie>) -> MoviesAdapter,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

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

    inner class ViewHolder(val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition)
            }
        }
    }
}