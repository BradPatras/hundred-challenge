package io.github.bradpatras.hundredchallenge.data

import androidx.lifecycle.LiveData
import io.github.bradpatras.hundredchallenge.HundredChallengeApplication
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ExerciseRepository(private val exerciseDao: ExerciseDao = HundredChallengeApplication.instance.database.exerciseDao()) {

    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAll()
    }

    fun updateExercises(exercises: List<Exercise>): Single<Unit> {
        return Single.just(exercises)
            .observeOn(Schedulers.io())
            .map { exerciseDao.updateAll(it) }
    }

    fun insertExercises(exercises: List<Exercise>): Single<Unit> {
        return Single.just(exercises)
            .observeOn(Schedulers.io())
            .map { exerciseDao.insertAll(it) }
    }

//    fun resetExercises() {
//        exercises = ExerciseDataSet.fetch()
//        liveDataExercises.postValue(exercises)
//    }

    companion object {
        fun createDefaultExercises(): List<Exercise> {
            val exerciseTitles: List<String> = listOf(
                "Push Ups",
                "Squats",
                "V Ups",
                "Scissor Kicks",
                "Donkey Kicks",
                "Fire Hydrants",
                "Russian Twists"
            )

            val exercises: MutableList<Exercise> = mutableListOf()
            for ((index, exerciseTitle) in exerciseTitles.withIndex()) {
                exercises.add(Exercise(index.toLong(), exerciseTitle, 0, 100))
            }

            return exercises
        }
    }
}