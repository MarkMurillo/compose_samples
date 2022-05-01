package com.example.composesamples.data

import com.example.composesamples.R
import kotlinx.coroutines.delay
import kotlin.random.Random

class LandscapeGenerator {
    companion object {
        val rngSeed = Random(System.currentTimeMillis())

        val LANDSCAPE_IDS = arrayOf(
            R.drawable.landscape1,
            R.drawable.landscape2,
            R.drawable.landscape3,
            R.drawable.landscape4,
        )
    }

    suspend fun getNextPage(): List<Int> {
        //delay(rngSeed.nextLong(200, 2000))
        delay(2000)
        return LANDSCAPE_IDS.toList()
    }

    fun getInitialList(): List<Int> = LANDSCAPE_IDS.toList()
}