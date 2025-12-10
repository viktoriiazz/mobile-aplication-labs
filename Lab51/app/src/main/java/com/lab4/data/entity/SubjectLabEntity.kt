package com.lab4.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "subjectsLabs",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subject_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SubjectLabEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "subject_id")
    val subjectId: Int,

    val title: String,
    val description: String,

    // 🔥 ТРИ статуси
    val inProgress: Boolean = false,
    val isCompleted: Boolean = false,
    val isPostponed: Boolean = false,

    val comment: String? = null
)
