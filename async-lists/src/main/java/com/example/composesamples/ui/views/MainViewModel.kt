package com.example.composesamples.ui.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composesamples.data.LandscapeGenerator

class MainViewModel: ViewModel() {
    private val imageGenerator = LandscapeGenerator()

    private var _curList = MutableLiveData<List<Int>>()
    var curList: LiveData<List<Int>> = _curList

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    init {
        _curList.postValue(imageGenerator.getInitialList().toMutableList())
    }

    suspend fun getNextPage() {
        _isLoading.postValue(true)
        val nextPage = imageGenerator.getNextPage()
        val newList = (_curList.value ?: mutableListOf()) + nextPage
        _isLoading.postValue(false)
        _curList.postValue(newList)

    }
}