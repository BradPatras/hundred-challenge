package io.github.bradpatras.hundredchallenge.data

import androidx.room.*

data class Exercise(
    var id: Int,
    var title: String,
    var progress: Int = 0,
    var total: Int = 100
)