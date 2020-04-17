package com.theapache64.topcorn.data.remote

import com.theapache64.twinkill.network.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * This is a single route API to get IMDB top 250 movies
 */
interface ApiInterface {

    @GET("top250_min.json")
    fun getTop250Movies(): Flow<Resource<List<Movie>>>
}