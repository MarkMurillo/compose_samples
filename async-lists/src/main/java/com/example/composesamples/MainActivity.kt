package com.example.composesamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composesamples.ui.theme.ComposeSamplesTheme
import com.example.composesamples.ui.views.MainView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply general app theme.
            ComposeSamplesTheme {
                MainView()
            }
        }
    }
}