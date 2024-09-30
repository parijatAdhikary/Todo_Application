package com.example.todoapplication.presentation.viewmodes

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.Task
import com.example.todoapplication.data.repositoryimplement.TaskRepositoryImplement
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepositoryImplement) : ViewModel() {
    var tasks = mutableStateListOf<Task>()

    init {
        getTasks()
    }

    private fun getTasks() {
        Log.d("testingTAG", "getTasks: Called ")
        viewModelScope.launch {
            tasks.clear()
            tasks.addAll(repository.getAllTasks())
        }
    }

    fun addTask(task: Task) {
        Log.d("testingTAG", "addTask: Called ")
        viewModelScope.launch {
            repository.insert(task)
            getTasks()  // Refresh the task list
        }
    }

    fun deleteTask(taskId: Long) {
        Log.d("testingTAG", "deleteTask: Called ")
        viewModelScope.launch {
            repository.deleteTaskById(taskId)
            tasks.clear()
            tasks.addAll(repository.getAllTasks())
        }
    }

    fun editTask(taskId: Long,taskName: String,taskDate: String) {
        Log.d("testingTAG", "editTask: Called ")
        viewModelScope.launch {
            repository.updateTaskById(taskId,taskName,taskDate)
            tasks.clear()
            tasks.addAll(repository.getAllTasks())
        }
    }
}