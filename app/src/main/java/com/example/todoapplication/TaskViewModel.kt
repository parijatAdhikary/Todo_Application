package com.example.todoapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    var tasks = mutableStateListOf<Task>()

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            tasks.clear()
            tasks.addAll(repository.getAllTasks())
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
            getTasks()  // Refresh the task list
        }
    }
}