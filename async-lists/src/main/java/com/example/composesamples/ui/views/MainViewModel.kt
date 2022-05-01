package com.example.composesamples.ui.views

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composesamples.data.LandscapeGenerator
import com.example.composesamples.data.LandscapeImage

class MainViewModel: ViewModel() {
    private val imageGenerator = LandscapeGenerator()

    // We can use MutableLiveData
    private var _curList = MutableLiveData<List<LandscapeImage>>()
    var curList: LiveData<List<LandscapeImage>> = _curList

    // Or preferably with Compose, we can use MutableState
    // to bypass the extra step of converting the LiveData into a state.
    var isLoading = mutableStateOf(false)


    suspend fun getNextPage(nextPageNum: Int): Boolean {
        isLoading.value = true
        val nextPage = imageGenerator.getNextPageLandscapeImages(nextPageNum)
        val isLast = nextPage.second

        if (isLast) {
            isLoading.value = false
            return false
        }

        val newList = (_curList.value ?: listOf()) + nextPage.first
        isLoading.value = false
        _curList.postValue(newList)
        return true
    }
}