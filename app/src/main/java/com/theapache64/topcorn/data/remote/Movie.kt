package com.theapache64.topcorn.data.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable


@Entity(
    tableName = "movies",
    indices = [
        Index("imdbUrl", unique = true)
    ]
)
data class Movie(
    @Json(name = "actors")
    val actors: List<String>,
    @Json(name = "desc")
    val desc: String, // Yôjinbô is a movie starring Toshirô Mifune, Eijirô Tôno, and Tatsuya Nakadai. A crafty ronin comes to a town divided by two criminal gangs and decides to play them against each other to free the town.
    @Json(name = "directors")
    val directors: List<String>,
    @Json(name = "genre")
    val genre: List<String>,
    @Json(name = "image_url")
    val imageUrl: String, // https://m.media-amazon.com/images/M/MV5BZThiZjAzZjgtNDU3MC00YThhLThjYWUtZGRkYjc2ZWZlOTVjXkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_.jpg
    @Json(name = "thumb_url")
    val thumbUrl: String,
    @Json(name = "imdb_url")
    val imdbUrl: String, // /title/tt0055630/
    @Json(name = "name")
    val name: String, // Yôjinbô
    @Json(name = "rating")
    val rating: Float, // 8.2
    @Json(name = "year")
    val year: Int?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}