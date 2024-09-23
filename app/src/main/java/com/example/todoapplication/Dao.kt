package com.example.todoapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(item:String,date:String,): List<Task>

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}