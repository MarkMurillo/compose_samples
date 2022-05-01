package com.example.composesamples.ui.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.composesamples.data.LandscapeImage
import com.example.composesamples.ui.theme.White
import com.example.composesamples.ui.views.widgets.InfiniteScrollList
import com.example.composesamples.ui.views.widgets.ListRow
import com.example.composesamples.ui.views.widgets.ProgressView

@Preview(showBackground = true)
@Composable
fun MainView(viewModel: MainViewModel = MainViewModel()) {
    // Surface is usually used to control background color
    Surface(
        color = White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LandscapeList(viewModel = viewModel)
    }
}

@Composable
fun LandscapeList(viewModel: MainViewModel) {
    // Get a state object from our mutable list so that changes
    // to this list will trigger a recompose.
    // In order to use the items in this state object, we need
    // to access them using property delegation with the keyword 'by'.
    // This means that the imageItems we declare here is actually
    // backed by the value of the State object we get from observeAsState()
    val imageItems by viewModel.curList.observeAsState()

    val isLoading = viewModel.isLoading

    val lazyListState = rememberLazyListState()

    InfiniteScrollList<LandscapeImage>(
        listState = lazyListState,
        listItems = imageItems,
        listItemView = {
            val curContext = LocalContext.current

            ListRow(
                landscapeImage = it,
                modifier = Modifier.clickable(
                    remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                    onClick = {
                        Toast.makeText(curContext, "Wow...", Toast.LENGTH_SHORT).show()
                    }
                )
            )
        },
        emptyView = {
            Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No items available.", color = White)
                }
            }
        },
        loadNextPage = {
            viewModel.getNextPage(it)
        },
        loadingOverlay = { loading ->
            if(loading) ProgressView(modifier = Modifier.fillMaxSize())
        },
        isLoading = isLoading
    )

}