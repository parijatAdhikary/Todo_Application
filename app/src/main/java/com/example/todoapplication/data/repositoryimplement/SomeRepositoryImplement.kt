package com.example.todoapplication.data.repositoryimplement

import com.example.todoapplication.data.source.SomeRepositoryInterface
import javax.inject.Inject

class SomeRepositoryImplement @Inject constructor():SomeRepositoryInterface{
    override fun fetchData(): String {
        return "Hello from the Repository!"
    }
}
