package com.theapache64.topcorn.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.databinding.ItemFeedBinding
import com.theapache64.topcorn.models.FeedItem

class FeedAdapter(
    private val onMovieClicked: (movie: Movie, mcvPoster: MaterialCardView, tvTitle: TextView) -> Unit
) :
    ListAdapter<FeedItem, FeedAdapter.ViewHolder>(FeedDiffCallback()) {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }

        val binding = ItemFeedBinding.inflate(inflater!!, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    /**
     * Adapter
     */
    inner class ViewHolder(val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {

        private val moviesAdapter by lazy {
            val adapter = MoviesAdapter(onMovieClicked).apply {
                setHasStableIds(true)
                stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            binding.rvMovies.adapter = adapter
            adapter
        }

        fun bind(feed: FeedItem) {
            binding.feed = feed
            moviesAdapter.submitList(feed.movies)
        }
    }
}

class FeedDiffCallback : DiffUtil.ItemCallback<FeedItem>() {

    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem == newItem
    }
}