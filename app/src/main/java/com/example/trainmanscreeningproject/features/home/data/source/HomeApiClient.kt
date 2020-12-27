package com.example.trainmanscreeningproject.features.home.data.source

import com.example.trainmanscreeningproject.features.home.data.entities.GifData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiClient {
    @GET(DISCOVER_GIFS)
    suspend fun getCurrentVersion(
        @Query("limit") limit:Int
    ): Response<GifData>

    companion object{
        const val DISCOVER_GIFS = "v1/gifs/trending?limit=10"
    }
}