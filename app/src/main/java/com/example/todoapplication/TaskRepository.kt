package com.example.todoapplication

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    suspend fun getAllTasks(): List<Task> {
        return withContext(Dispatchers.IO) {
            taskDao.getAllTasks()
        }
    }
}