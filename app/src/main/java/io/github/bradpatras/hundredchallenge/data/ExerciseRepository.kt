package io.github.bradpatras.hundredchallenge.data

class ExerciseRepository() {
    fun getAllExercises(): List<Exercise> {
        return ExerciseDataSet.fetch()
    }
}