package io.github.bradpatras.hundredchallenge.data

import androidx.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAll()
    }

    fun updateExercises(exercises: List<Exercise>): Single<Unit> {
        return Single.just(exercises)
            .observeOn(Schedulers.io())
            .map { exerciseDao.updateAll(it) }
    }

//    fun resetExercises() {
//        exercises = ExerciseDataSet.fetch()
//        liveDataExercises.postValue(exercises)
//    }
}