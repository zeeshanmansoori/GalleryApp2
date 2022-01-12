package com.google.galleryApp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.galleryApp.data.PhotoPagingSource
import com.google.galleryApp.domain.model.Photo
import com.google.galleryApp.domain.repository.PhotosRepository


class PhotoRepositoryImpl(private val retrofitApi: RetrofitApi) :
    PhotosRepository {

    override fun getRecentPhotos() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
        ),
        pagingSourceFactory = { PhotoPagingSource(retrofitApi, "") }
    ).liveData

    override fun searchPhotos(query: String): LiveData<PagingData<Photo>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
        ),
        pagingSourceFactory = { PhotoPagingSource(retrofitApi, query) }
    ).liveData

}