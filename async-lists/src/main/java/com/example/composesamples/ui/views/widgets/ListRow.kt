package com.example.composesamples.ui.views.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composesamples.data.LandscapeImage
import kotlin.random.Random

@Composable
fun ListRow(landscapeImage: LandscapeImage, modifier: Modifier = Modifier) {
    val sharedModifier = modifier.fillMaxWidth()
        .height(250.dp)

    val loading = remember { mutableStateOf(false) }
    val rand = remember {
        Random(System.currentTimeMillis())
    }

    val randomColor = remember {
        Color(rand.nextInt(0, 255), rand.nextInt(0, 255), rand.nextInt(0, 255))
    }

    Surface(color = randomColor) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = sharedModifier.padding(24.dp)
        ) {

            Box(modifier = sharedModifier, contentAlignment = Alignment.Center) {

                val alpha = if (loading.value) 0f else 1f

                AsyncImage(
                    model = landscapeImage.url,
                    contentDescription = "Test image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.alpha(alpha),
                    onLoading = {
                        loading.value = true
                    },
                    onSuccess = {
                        loading.value = false
                    },
                    onError = {
                        loading.value = false
                    }
                )

                ProgressView(modifier = Modifier.alpha(1f - alpha).fillMaxSize())
            }
        }
    }
}