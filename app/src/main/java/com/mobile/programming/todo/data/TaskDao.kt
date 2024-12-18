package com.mobile.programming.todo.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg task: Task): List<Long>

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("DELETE FROM task_table WHERE id = :taskId")
    suspend fun delete(taskId: Int)

    @Update
    suspend fun update(task: Task)

}
