package com.example.todoapplication.presentation.viewmodes


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapplication.data.repositoryimplement.SomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: SomeRepository
) : ViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    init {

        loadData()
    }

    private fun loadData() {
        Log.d("testingTAG", "loadData: Called ")
        _data.value = repository.fetchData()
    }

}
