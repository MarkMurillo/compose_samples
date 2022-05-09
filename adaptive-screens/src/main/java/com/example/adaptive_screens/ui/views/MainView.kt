package com.example.adaptive_screens.ui.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainView(windowSizeClass: WindowSizeClass, viewModel: MainViewModel = MainViewModel()) {

    // Handle the different sizes for width.
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            GenericList(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), viewModel = viewModel
            )
        }
        else -> {
            // Split the view into two and display the selected item.
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                GenericList(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(), viewModel = viewModel
                )
                ListDetail(
                    viewModel = viewModel,
                    modifier = Modifier
                        .padding(start = 0.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }

        }
    }
}

@Composable
fun ListDetail(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val selectedIndex by viewModel.selectedIndex

    Card(modifier = modifier) {
        Surface(color = Color.White) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selected Text Index #${selectedIndex}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun GenericList(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    val state = rememberLazyListState()
    val listItems = viewModel.listItems

    LazyColumn(state = state, modifier = modifier) {
        itemsIndexed(listItems) { index, curItem ->
            val context = LocalContext.current

            Card(
                shape = RoundedCornerShape(10.dp), modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                    .clickable(
                        remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                        onClick = {
                            viewModel.onSelectIndex(index)

                            Toast
                                .makeText(context, "Selected #$index", Toast.LENGTH_SHORT)
                                .show()
                        }
                    ),
                elevation = 4.dp
            ) {
                Surface(color = Color.White) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "List item #$curItem",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}