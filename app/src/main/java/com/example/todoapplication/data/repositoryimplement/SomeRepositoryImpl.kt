package com.example.todoapplication.data.repositoryimplement

import com.example.todoapplication.data.source.SomeRepository
import javax.inject.Inject

class SomeRepositoryImpl @Inject constructor() : SomeRepository {
    override fun fetchData(): String {
        return "Hello from the Repository!"
    }
}
