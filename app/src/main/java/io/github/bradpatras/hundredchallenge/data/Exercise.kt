package io.github.bradpatras.hundredchallenge.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "exercise_title") var title: String,
    @ColumnInfo(name = "exercise_progress") var progress: Int,
    @ColumnInfo(name = "exercise_total") var total: Int
)

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise ORDER by id ASC")
    fun getAllLiveData(): LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise ORDER by id ASC")
    fun getAll(): List<Exercise>

    @Update
    fun updateAll(tasks: List<Exercise>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(exercises: List<Exercise>)
}
