package com.theapache64.topcorn.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.theapache64.topcorn.data.remote.Movie
import com.theapache64.topcorn.databinding.ItemMovieBinding

class MoviesAdapter(
    private val movies: List<Movie>,
    private val callback: (movie: Movie, mcvPoster: MaterialCardView, tvTitle: TextView) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemMovieBinding.inflate(inflater!!, parent, false)
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
                callback(movies[layoutPosition], binding.mcvPoster, binding.tvTitle)
            }
        }
    }
}
