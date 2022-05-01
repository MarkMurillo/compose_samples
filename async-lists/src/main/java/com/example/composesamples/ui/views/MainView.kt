package com.example.composesamples.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesamples.ui.theme.White
import com.example.composesamples.ui.views.widgets.ListRow
import com.example.composesamples.ui.views.widgets.ProgressView
import kotlinx.coroutines.flow.filter

@Preview(showBackground = true)
@Composable
fun MainView(viewModel: MainViewModel = MainViewModel()) {
    // Surface is usually used to control background color
    Surface(
        color = White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val progressVisibility by viewModel.isLoading.observeAsState(initial = false)

        ImageList(viewModel = viewModel)

        // Control visibility of progress view from the ViewModel.
        AnimatedVisibility(
            visible = progressVisibility,
            enter = fadeIn(
                animationSpec = keyframes {
                    durationMillis = 500
                }
            ),
            exit = fadeOut(),
        ) {
            ProgressView()
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
                ListRow(landscapeImage = item)
            }
        }
    }

    val loadMoreState = remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleIndex = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleIndex == totalItemsNumber - 1
        }
    }

    // Add a side-effect to detect end of list and load more items.
    LaunchedEffect(loadMoreState) {
        // Check the last visible index everytime we scroll and the
        // visible item indexes change
        snapshotFlow { loadMoreState.value }
            .filter { it }
            .collect {
                // Load more data.
                viewModel.getNextPage()
            }
    }
}