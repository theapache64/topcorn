package com.theapache64.topcorn.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theapache64.topcorn.R
import com.theapache64.topcorn.data.local.FavoriteMovie
import com.theapache64.topcorn.data.remote.Movie
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoritesAdapter(
    private val favorites: List<FavoriteMovie>,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoritesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
    )

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favorite = favorites[position]
        with(holder) {
            title.text = favorite.name

            Glide.with(itemView)
                .load(favorite.imageUrl)
                .into(poster)

            itemView.setOnClickListener {
                clickListener(
                    Movie(
                        favorite.actors ?: emptyList(),
                        favorite.desc ?: "",
                        favorite.directors ?: emptyList(),
                        favorite.genre ?: emptyList(),
                        favorite.imageUrl ?: "",
                        favorite.thumbUrl ?: "",
                        favorite.imdbUrl ?: "",
                        favorite.name ?: "",
                        favorite.rating ?: 0F,
                        favorite.year ?: 0
                    )
                )
            }
        }
    }

    override fun getItemCount() = favorites.size

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.tv_title
        val poster: ImageView = view.iv_poster
    }
}