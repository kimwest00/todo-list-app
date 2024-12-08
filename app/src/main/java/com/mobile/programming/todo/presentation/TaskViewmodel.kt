package com.mobile.programming.todo.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.programming.todo.data.Task
import com.mobile.programming.todo.data.TaskDatabase
import com.mobile.programming.todo.util.mainScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()
    var insertResult = MutableLiveData<List<Long>>()
    private val coroutineIOScope = CoroutineScope(IO)


    fun insertTask(task: Task) {
        CoroutineScope(Dispatchers.Main).launch {
            _insertTask(task)
        }
    }

    private suspend fun _insertTask(task: Task): List<Long> {
        insertResult = asyncInsertTask(task)
        return insertResult.value as List<Long>
    }

    // insert task
    // use couroutineIOScope
    private suspend fun asyncInsertTask(task: Task): MutableLiveData<List<Long>> {
        val insertReturn = coroutineIOScope.async(IO) {
            return@async taskDao.insert(task)
        }.await()
        return mainScope.async {
            insertResult.value = insertReturn
            insertResult
        }.await()
    }

    // delete task
    // use viewmodelScope
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
