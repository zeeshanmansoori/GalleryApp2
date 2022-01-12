package com.google.galleryApp.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.galleryApp.domain.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PhotosRepository,
) : ViewModel() {

    private val _currentQuery: MutableLiveData<String?> = MutableLiveData(null)
     val currentQuery: LiveData<String?> get() = _currentQuery
    private var currentJob: Job? = null

    val photos = _currentQuery.switchMap {
        if (it.isNullOrEmpty())
            return@switchMap MutableLiveData(PagingData.empty())
        repository.searchPhotos(it).cachedIn(viewModelScope)
    }

    fun startSearch(query: String) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            delay(1000)
            _currentQuery.value = query
        }

    }

    fun clearSearch() {
        currentJob?.cancel()
        _currentQuery.value = null
    }
}