package com.lab4.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lab4.data.entity.SubjectEntity

@Dao
interface SubjectDao {

    @Insert
    suspend fun addSubjectReturningId(subject: SubjectEntity): Long

    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<SubjectEntity>

    @Query("SELECT * FROM subjects WHERE id = :id LIMIT 1")
    suspend fun getSubjectById(id: Int): SubjectEntity?

    @Query("SELECT * FROM subjects WHERE title = :title LIMIT 1")
    suspend fun getSubjectByTitle(title: String): SubjectEntity?
}

