package com.example.composesamples.ui.views.widgets

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composesamples.ui.theme.OverlayBackgroundColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProgressView(modifier: Modifier = Modifier) {
    // Background
    Surface(
        color = OverlayBackgroundColor,
        modifier = modifier
    ) {
        // Container type
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.wrapContentSize())
        }
    }
}