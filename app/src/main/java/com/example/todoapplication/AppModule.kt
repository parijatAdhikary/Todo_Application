package com.example.todoapplication

import com.example.todoapplication.data.repositoryimplement.SomeRepositoryImpl
import com.example.todoapplication.data.repositoryimplement.TaskRepositoryImpl
import com.example.todoapplication.data.source.SomeRepository
import com.example.todoapplication.data.source.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    @ViewModelScoped
    fun provideSomeRepository(): SomeRepository {
        return SomeRepositoryImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

}

