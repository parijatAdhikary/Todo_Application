package com.example.todoapplication.data.repositoryimplement

import com.example.todoapplication.Task
import com.example.todoapplication.TaskDao
import com.example.todoapplication.data.source.TaskRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TaskRepositoryImplement (private val taskDao: TaskDao) : TaskRepositoryInterface {
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
        taskDao.updateTaskById(taskId,taskName,taskDate)
    }
}