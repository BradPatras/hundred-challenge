package io.github.bradpatras.hundredchallenge

import android.app.Application

class HundredChallengeApplication: Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        lateinit var instance: HundredChallengeApplication
    }
}