package com.example.todoapplication.data.source

import com.example.todoapplication.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface TaskRepository {
    suspend fun insert(task: Task)
    suspend fun getAllTasks(): List<Task>
    suspend fun deleteTaskById(taskId: Long)
    suspend fun updateTaskById(taskId: Long, taskName: String, taskDate: String)
}