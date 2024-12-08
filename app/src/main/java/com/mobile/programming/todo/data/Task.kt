package com.mobile.programming.todo.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    var isCompleted: Boolean = false,
    var imgUri: Bitmap? = null,
)