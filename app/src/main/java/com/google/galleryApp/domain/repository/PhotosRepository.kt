package com.google.galleryApp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.google.galleryApp.domain.model.Photo

interface PhotosRepository {
    fun getRecentPhotos(): LiveData<PagingData<Photo>>
    fun searchPhotos(query: String): LiveData<PagingData<Photo>>
}