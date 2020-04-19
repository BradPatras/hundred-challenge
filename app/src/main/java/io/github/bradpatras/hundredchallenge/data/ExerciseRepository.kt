package io.github.bradpatras.hundredchallenge.data

import androidx.lifecycle.MutableLiveData

object ExerciseRepository {
    private var exercises: List<Exercise> = ExerciseDataSet.fetch()

    val liveDataExercises: MutableLiveData<List<Exercise>> by lazy {
        val liveData = MutableLiveData<List<Exercise>>()
        liveData.postValue(exercises)
        liveData
    }

    fun resetExercises() {
        exercises = ExerciseDataSet.fetch()
        liveDataExercises.postValue(exercises)
    }
}