package com.example.adaptive_screens.ui.views

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val listItems = (0..30).toList()
    var selectedIndex = mutableStateOf(0)

    fun onSelectIndex(index: Int) {
        selectedIndex.value = index
    }
}