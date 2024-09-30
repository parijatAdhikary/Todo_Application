package com.example.todoapplication.data.repositoryimplement

import com.example.todoapplication.Task
import com.example.todoapplication.TaskDao
import com.example.todoapplication.data.source.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    override suspend fun getAllTasks(): List<Task> {
        return withContext(Dispatchers.IO) {
            taskDao.getAllTasks()
        }
    }

    override suspend fun deleteTaskById(taskId: Long) {
        taskDao.deleteTaskById(taskId)
    }

    override suspend fun updateTaskById(taskId: Long, taskName: String, taskDate: String) {
        taskDao.updateTaskById(taskId, taskName, taskDate)
    }

}