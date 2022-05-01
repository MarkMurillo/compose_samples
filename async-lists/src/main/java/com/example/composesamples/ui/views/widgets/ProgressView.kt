package com.example.composesamples.ui.views.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composesamples.ui.theme.OverlayBackgroundColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProgressView() {
    // Background
    Surface(
        color = OverlayBackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        // Container type
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.wrapContentSize())
        }
    }
}