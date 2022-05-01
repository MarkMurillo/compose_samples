package com.example.composesamples.data

import com.example.composesamples.R
import kotlinx.coroutines.delay
import kotlin.random.Random

class LandscapeGenerator {
    companion object {
        val rngSeed = Random(System.currentTimeMillis())

        val LANDSCAPE_DRAWABLE_IDS = arrayOf(
            R.drawable.landscape1,
            R.drawable.landscape2,
            R.drawable.landscape3,
            R.drawable.landscape4,
        )

        val LANDSCAPE_URLS = arrayOf(
            "https://images.unsplash.com/photo-1506744038136-46273834b3fb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80",
            "https://images.unsplash.com/photo-1511884642898-4c92249e20b6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80",
            "https://images.unsplash.com/photo-1434725039720-aaad6dd32dfe?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2842&q=80",
            "https://images.unsplash.com/photo-1501785888041-af3ef285b470?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80"
        )

        const val MAX_PAGES = 2
    }

    suspend fun getNextPageIds(): List<Int> {
        delay(2000)
        return LANDSCAPE_DRAWABLE_IDS.toList()
    }

    suspend fun getNextPageLandscapeImages(page: Int): Pair<List<LandscapeImage>, Boolean> {
        //delay(rngSeed.nextLong(200, 2000))
        delay(2000)
        if (page >= MAX_PAGES) return Pair(emptyList(), true)
        return Pair(LANDSCAPE_URLS.map { LandscapeImage(it) }, false)
    }

    fun getInitialList(): List<Int> = LANDSCAPE_DRAWABLE_IDS.toList()

    fun getInitialLandscapeImageList(): List<LandscapeImage> = LANDSCAPE_URLS.map { LandscapeImage(it) }
}