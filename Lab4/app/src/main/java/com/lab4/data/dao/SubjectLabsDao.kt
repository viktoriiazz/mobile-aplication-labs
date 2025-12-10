package com.lab4.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lab4.data.entity.SubjectLabEntity

@Dao
interface SubjectLabsDao {

    @Query("SELECT * FROM subjectsLabs WHERE subject_id = :id")
    suspend fun getSubjectLabsBySubjectId(id: Int): List<SubjectLabEntity>

    @Insert
    suspend fun addSubjectLab(lab: SubjectLabEntity)

    @Update
    suspend fun updateLab(lab: SubjectLabEntity)

    @Query("""
        UPDATE subjectsLabs 
        SET inProgress = :inProgress, 
            isCompleted = :isCompleted, 
            isPostponed = :isPostponed 
        WHERE id = :labId
    """)
    suspend fun updateStatus(
        labId: Int,
        inProgress: Boolean,
        isCompleted: Boolean,
        isPostponed: Boolean
    )

    @Query("UPDATE subjectsLabs SET comment = :comment WHERE id = :labId")
    suspend fun updateComment(labId: Int, comment: String)

    @Query("SELECT * FROM subjectsLabs WHERE subject_id = :subjectId AND title = :title LIMIT 1")
    suspend fun getLabByTitleAndSubject(subjectId: Int, title: String): SubjectLabEntity?
}
