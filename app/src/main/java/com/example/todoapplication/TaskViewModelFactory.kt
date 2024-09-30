package com.example.todoapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapplication.data.repositoryimplement.TaskRepositoryImplement
import com.example.todoapplication.presentation.viewmodes.TaskViewModel


class TaskViewModelFactory(private val repository: TaskRepositoryImplement) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
