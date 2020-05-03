package io.github.bradpatras.hundredchallenge

import android.app.Application
import io.github.bradpatras.hundredchallenge.data.AppDatabase
import io.github.bradpatras.hundredchallenge.data.AppDatabaseHelper

class HundredChallengeApplication: Application() {
    lateinit var database: AppDatabase

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        database = AppDatabaseHelper.setupDatabase(this)
    }

    companion object {
        lateinit var instance: HundredChallengeApplication
    }
}