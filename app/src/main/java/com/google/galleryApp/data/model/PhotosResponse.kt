package com.google.galleryApp.data.model

import com.google.galleryApp.domain.model.Photo

data class PhotosResponse(
    val photos: Photos,
    val stat: String
) {

    companion object {
        fun PhotosResponse.toDomainPhotos(): List<Photo> {
            return photos.photo.map {
                Photo(id = it.id, title = it.title, url = it.url_s?:"")
            }
        }
    }
}