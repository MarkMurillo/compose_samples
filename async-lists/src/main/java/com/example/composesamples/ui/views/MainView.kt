package com.example.composesamples.ui.views

import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesamples.ui.theme.OverlayBackgroundColor
import com.example.composesamples.ui.theme.White
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Preview(showBackground = true)
@Composable
fun MainView(viewModel: MainViewModel = MainViewModel()) {
    // Surface is usually used to control background color
    Surface(
        color = White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ImageList(viewModel = viewModel)
        ProgressView(viewModel = viewModel)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProgressView(viewModel: MainViewModel) {
    val progressVisibility by viewModel.isLoading.observeAsState(initial = false)

    AnimatedVisibility(visible = progressVisibility) {
        Surface(
            color = OverlayBackgroundColor,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            }
        }
    }
}

@Composable
fun ImageList(viewModel: MainViewModel) {
    // Get a state object from our mutable list so that changes
    // to this list will trigger a recompose.
    // In order to use the items in this state object, we need
    // to access them using property delegation with the keyword 'by'.
    // This means that the imageItems we declare here is actually
    // backed by the value of the State object we get from observeAsState()
    val imageItems by viewModel.curList.observeAsState()

    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        imageItems?.let { imageIndexItems ->
            // Set our items
            items(imageIndexItems) { item ->
                // Just display the index value for now.
                Text(text = item.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 250.dp, 0.dp, 250.dp),
                    textAlign = TextAlign.Center)
            }
        }
    }

    // Add a side-effect to detect end of list and load more items.
    LaunchedEffect(lazyListState) {
        // Check the last visible index everytime we scroll and the
        // visible item indexes change
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .map { index ->
                // Everytime our index changes, get the list of visible items and check if the
                // last visible item's index is the last item in the actual list.
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1
            }
            // Only emit once it changes
            .distinctUntilChanged()
            // Only trigger if we have reached the bottom.
            .filter { it }
            .collect {
                // Load more data.
                viewModel.getNextPage()
            }
    }
}