package io.github.bradpatras.hundredchallenge

import android.app.Application
import androidx.room.Room
import io.github.bradpatras.hundredchallenge.data.AppDatabase

class HundredChallengeApplication: Application() {
    lateinit var database: AppDatabase

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AppDatabase::class.java, AppDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()

        preloadDatabaseIfNeeded()
    }

    fun preloadDatabaseIfNeeded() {

    }

    companion object {
        lateinit var instance: HundredChallengeApplication
    }
}