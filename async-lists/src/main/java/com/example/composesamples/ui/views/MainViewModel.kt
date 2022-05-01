package com.example.composesamples.ui.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composesamples.data.LandscapeGenerator
import com.example.composesamples.data.LandscapeImage

class MainViewModel: ViewModel() {
    private val imageGenerator = LandscapeGenerator()

    private var _curList = MutableLiveData<List<LandscapeImage>>()
    var curList: LiveData<List<LandscapeImage>> = _curList

    private var _isLoading = MutableLiveData<Boolean>().apply { value = false }
    var isLoading: LiveData<Boolean> = _isLoading


    suspend fun getNextPage(nextPageNum: Int): Boolean {
        _isLoading.postValue(true)
        val nextPage = imageGenerator.getNextPageLandscapeImages(nextPageNum)
        val isLast = nextPage.second

        if (isLast) {
            _isLoading.postValue(false)
            return false
        }

        val newList = (_curList.value ?: listOf()) + nextPage.first
        _isLoading.postValue(false)
        _curList.postValue(newList)
        return true
    }
}