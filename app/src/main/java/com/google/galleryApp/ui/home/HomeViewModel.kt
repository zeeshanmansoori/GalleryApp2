package com.google.galleryApp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.galleryApp.domain.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PhotosRepository,
) : ViewModel() {
    val photos = repository.getRecentPhotos().cachedIn(viewModelScope)
}