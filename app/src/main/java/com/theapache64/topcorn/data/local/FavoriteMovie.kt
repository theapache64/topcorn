package com.theapache64.topcorn.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "favorites")
data class FavoriteMovie(
    @Json(name = "actors")
    val actors: List<String>?,
    @Json(name = "desc")
    val desc: String?,
    @Json(name = "directors")
    val directors: List<String>?,
    @Json(name = "genre")
    val genre: List<String>?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "thumb_url")
    val thumbUrl: String?,
    @Json(name = "imdb_url")
    val imdbUrl: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "rating")
    val rating: Float?,
    @Json(name = "year")
    val year: Int?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}