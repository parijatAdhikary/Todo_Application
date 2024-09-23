package com.example.todoapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val todolist: String,
    val date: String,
    val completed: Boolean = false
)