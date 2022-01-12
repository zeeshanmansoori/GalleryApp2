package com.google.galleryApp.data.repository

import com.google.galleryApp.BuildConfig
import com.google.galleryApp.data.model.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {


    companion object {
        const val BASE_URL = "https://api.flickr.com/services/"
        const val apiKey = BuildConfig.api_key
    }

    @GET("rest")
    suspend fun recentPhotos(
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("per_page") perPage: Int = 20,
        @Query("page") currentPage: Int,
        @Query("api_key") apiKey: String = RetrofitApi.apiKey,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("extras") extras: String = "url_s",
    ): PhotosResponse

    @GET("rest")
    suspend fun searchPhotos(
        @Query("text") query: String,
        @Query("method") method: String = "flickr.photos.search",
        @Query("per_page") perPage: Int = 20,
        @Query("page") currentPage: Int,
        @Query("api_key") apiKey: String = RetrofitApi.apiKey,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("extras") extras: String = "url_s",
    ): PhotosResponse
}
