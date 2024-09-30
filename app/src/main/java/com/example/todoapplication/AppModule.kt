package com.example.todoapplication

import com.example.todoapplication.data.repositoryimplement.SomeRepositoryImpl
import com.example.todoapplication.data.source.SomeRepository
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
}

