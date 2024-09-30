package com.example.todoapplication

import com.example.todoapplication.data.repositoryimplement.TaskRepositoryImpl
import com.example.todoapplication.data.source.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TaskModule {

    @Provides
    @ViewModelScoped
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

}