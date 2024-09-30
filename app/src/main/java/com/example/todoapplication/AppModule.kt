package com.example.todoapplication
import com.example.todoapplication.data.repositoryimplement.SomeRepositoryImplement
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
    fun provideSomeRepository(): SomeRepositoryImplement {
        return SomeRepositoryImplement()
    }
}