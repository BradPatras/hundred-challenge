package io.github.bradpatras.hundredchallenge.data

import androidx.room.Database

@Database(entities = [Exercise::class], version = 1)
abstract class AppDatabase {

    abstract fun exerciseDao(): ExerciseDao

    companion object {
        const val NAME = "hundredChallengeDb"
    }
}