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

    fun insert(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    fun delete(taskId: Int) {
        viewModelScope.launch {
            taskDao.delete(taskId)
        }
    }
}