package com.theapache64.topcorn.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.databinding.ItemMovieBinding

class MoviesAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private val callback: (position: Int, mcvPoster: MaterialCardView, tvTitle: TextView) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return movies[position].id
    }

    override fun getItemCount(): Int = movies.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movie = movie
    }

    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition, binding.mcvPoster, binding.tvTitle)
            }
        }
    }
}
