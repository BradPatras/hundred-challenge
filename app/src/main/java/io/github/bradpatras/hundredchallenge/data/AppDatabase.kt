package io.github.bradpatras.hundredchallenge.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Exercise::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object {
        const val NAME = "hundredChallengeDb"
    }
}