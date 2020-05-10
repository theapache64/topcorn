package com.theapache64.topcorn.models

import com.theapache64.topcorn.data.remote.Movie


data class FeedItem(
    val id: Long,
    val genre: String,
    val movies: List<Movie>
)
