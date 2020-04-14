package io.github.bradpatras.hundredchallenge.data

import androidx.room.*

data class Exercise(
    var id: Int,
    var title: String,
    var progress: Double = 0.0
)