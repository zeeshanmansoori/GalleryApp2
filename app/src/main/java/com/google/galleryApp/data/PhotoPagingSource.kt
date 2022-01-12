package com.google.galleryApp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.galleryApp.data.model.PhotosResponse.Companion.toDomainPhotos
import com.google.galleryApp.data.repository.RetrofitApi
import com.google.galleryApp.domain.model.Photo
import com.google.galleryApp.util.log

class PhotoPagingSource(
    private val retrofitApi: RetrofitApi,
    private val query: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {

        val position = params.key ?: 1
        return try {
            val response = if (query.isEmpty()) retrofitApi.recentPhotos(currentPage = position) else retrofitApi.searchPhotos(query, currentPage = position)
            val photos = response.toDomainPhotos()
            LoadResult.Page(
                data = photos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {

            log("got exception $e",e)
            LoadResult.Error(e)
        }
    }
}