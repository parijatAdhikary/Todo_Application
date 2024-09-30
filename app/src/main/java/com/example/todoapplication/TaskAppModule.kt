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
object TaskAppModule {
    @Provides
    @ViewModelScoped
    fun provideSomeRepository(): TaskRepository {
        return TaskRepositoryImpl(null)
    }
}