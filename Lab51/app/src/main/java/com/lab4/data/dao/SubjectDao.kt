package com.lab4.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lab4.data.entity.SubjectEntity

@Dao
interface SubjectDao {

    @Insert
    suspend fun addSubject(subject: SubjectEntity)

    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<SubjectEntity>

    @Query("SELECT * FROM subjects WHERE id = :id")
    suspend fun getSubjectById(id: Int): SubjectEntity?
}
