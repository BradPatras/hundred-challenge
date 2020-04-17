package io.github.bradpatras.hundredchallenge.ui.main

import androidx.lifecycle.ViewModel
import io.github.bradpatras.hundredchallenge.data.Exercise
import io.github.bradpatras.hundredchallenge.data.ExerciseRepository

class MainViewModel : ViewModel() {
    var exercises: List<Exercise> = ExerciseRepository().getAllExercises()
    var timerController: TimerController = TimerController()
}
