package com.example.composesamples.ui.views.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.filter

@Composable
fun <T> InfiniteScrollList(listItems: List<T>?,
                           listItemView: @Composable (curItem: T) -> Unit,
                           emptyView: @Composable () -> Unit,
                           loadNextPage: suspend (nextPage: Int) -> Boolean,
                           loadingOverlay: @Composable ((isLoading: Boolean) -> Unit)? = null,
                            // We need this to be a state boolean since we should be
                            // listening to the latest updates.
                           isLoading: State<Boolean?>
) {
    val listState = rememberLazyListState()
    val hasMoreItems = remember { mutableStateOf(true) }
    val displayEmptyList = remember { mutableStateOf(false) }

    // Check if we have an empty list
    LaunchedEffect(listItems) {
        // When we recompose, check if the imageItems are null or empty and
        // attempt to get the next page.
        if (listItems.isNullOrEmpty() && hasMoreItems.value) {
            // Try loading initial data.
            val hasMore = loadNextPage(0)
            hasMoreItems.value = hasMore
        }

        // Check if we have no items AND there are no more items from the provider
        if (listItems.isNullOrEmpty() && !hasMoreItems.value) {
            displayEmptyList.value = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (displayEmptyList.value) {
            emptyView()
        } else {
            // LazyColumn is the equivalent of RecyclerView where
            // it only renders enough to display on the screen.
            LazyColumn(state = listState) {
                listItems?.let { curItems ->
                    // Set our items
                    items(curItems) { item ->
                        // Just display the index value for now.
                        listItemView(item)
                    }
                }
            }
        }

        loadingOverlay?.let {
            it(isLoading.value ?: false)
        }
    }
    // Add infinite scrolling side effect.
    listState.OnNextPageLoad(loadNextPage)
}

@Composable
fun LazyListState.OnNextPageLoad(loadNextPage: suspend (nextPage: Int) -> Boolean) {
    val currentLoadedPage = remember { mutableStateOf(0) }
    val hasMoreItems = remember { mutableStateOf(true) }

    val loadMoreState =
        derivedStateOf {
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            // If the last item in the list is visible, and there are more items,
            // we should attempt to load.
            lastVisibleIndex == totalItemsNumber - 1 && hasMoreItems.value
        }

    // Add a side-effect to detect end of list and load more items.
    LaunchedEffect(loadMoreState) {
        // Check the last visible index everytime we scroll and the
        // visible item indexes change
        snapshotFlow { loadMoreState.value }
            .filter { it }
            .collect {
                // Load more data.
                val hasMore = loadNextPage(currentLoadedPage.value + 1)
                hasMoreItems.value = hasMore
                if (hasMore) currentLoadedPage.value += 1
            }
    }
}