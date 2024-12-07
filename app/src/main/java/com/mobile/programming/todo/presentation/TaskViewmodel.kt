package com.mobile.programming.todo.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mobile.programming.todo.data.Task
import com.mobile.programming.todo.data.TaskDatabase
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    // Task 추가
    fun insert(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    // Task 삭제
    fun delete(taskId: Int) {
        viewModelScope.launch {
            taskDao.delete(taskId)
        }
    }

    // Task 업데이트 (isCompleted 상태 변경 포함)
    fun update(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }
}
