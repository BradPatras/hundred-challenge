package io.github.bradpatras.hundredchallenge.data

import android.content.Context
import androidx.room.Room
import io.github.bradpatras.hundredchallenge.R
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

// Helper class for setting up the database instance and
// adding the initial items on the first open of the app
class AppDatabaseHelper {
    companion object {
        private const val DB_PRELOADED_KEY = "dbPreloadedKey"
        fun setupDatabase(context: Context): AppDatabase {
            val database = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build()

            // Check for the preloaded flag, if the flag doesn't exist then it's the first time
            // the app has been opened or since the app data has been cleared and we need to preload
            // the database
            val prefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            if (!prefs.contains(DB_PRELOADED_KEY)) {
                preloadDatabase(database)
                prefs.edit()
                    .putBoolean(DB_PRELOADED_KEY, true)
                    .apply()
            }

            return database
        }

        private fun preloadDatabase(database: AppDatabase) {
            val exercises = ExerciseRepository.createDefaultExercises()
            Single.just(exercises)
                .observeOn(Schedulers.io())
                .map { database.exerciseDao().insertAll(it) }
                .subscribe()
        }
    }
}