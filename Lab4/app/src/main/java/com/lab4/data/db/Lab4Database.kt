package com.lab4.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Index
import com.lab4.data.dao.SubjectDao
import com.lab4.data.dao.SubjectLabsDao
import com.lab4.data.entity.SubjectEntity
import com.lab4.data.entity.SubjectLabEntity
import kotlinx.coroutines.*

@Database(
    entities = [SubjectEntity::class, SubjectLabEntity::class],
    version = 3,
    exportSchema = false
)
abstract class Lab4Database : RoomDatabase() {
    abstract val subjectsDao: SubjectDao
    abstract val subjectLabsDao: SubjectLabsDao
}

object DatabaseStorage {

    private val coroutineScope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.IO +
                CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    )

    private var _database: Lab4Database? = null

    fun getDatabase(context: Context): Lab4Database {
        if (_database != null) return _database!!

        _database = Room.databaseBuilder(
            context,
            Lab4Database::class.java,
            "lab4Database"
        )
            .fallbackToDestructiveMigration() // очищає старі дублікати
            .build()

        preloadData()

        return _database!!
    }

    private fun preloadData() {

        coroutineScope.launch {

            val db = _database ?: return@launch


            suspend fun getOrCreateSubject(title: String): Int {
                val existing = db.subjectsDao.getSubjectByTitle(title)
                if (existing != null) return existing.id!!

                val newId = db.subjectsDao.addSubjectReturningId(SubjectEntity(title = title))
                return newId.toInt()
            }

            // Реальні id предметів
            val cyberId = getOrCreateSubject("Кіберфізичні системи")
            val networksId = getOrCreateSubject("Комп’ютерні мережі")
            val mobAppsId = getOrCreateSubject("Програмування мобільних додатків")
            val itpmId = getOrCreateSubject("Управління IT-проєктами")
            val dbId = getOrCreateSubject("Бази даних")


            suspend fun addLabIfNotExists(
                subjectId: Int,
                title: String,
                description: String,
                inProgress: Boolean = false,
                isCompleted: Boolean = false,
                isPostponed: Boolean = false,
                comment: String? = null
            ) {
                val exists = db.subjectLabsDao.getLabByTitleAndSubject(subjectId, title)
                if (exists != null) return

                db.subjectLabsDao.addSubjectLab(
                    SubjectLabEntity(
                        subjectId = subjectId,
                        title = title,
                        description = description,
                        inProgress = inProgress,
                        isCompleted = isCompleted,
                        isPostponed = isPostponed,
                        comment = comment
                    )
                )
            }



            // Кіберфізичні системи
            addLabIfNotExists(
                cyberId,
                "ЛР1. Робота з сенсорами",
                "Дослідження освітлення, температури, тиску.",
                isCompleted = true,
                comment = "Гарний результат"
            )
            addLabIfNotExists(
                cyberId,
                "ЛР2. ESP32. Таймери та GPIO",
                "Таймери, переривання, робота з GPIO.",
                inProgress = true
            )

            // Комп’ютерні мережі
            addLabIfNotExists(
                networksId,
                "ЛР1. VLAN конфігурація",
                "Trunk, access, сегментація.",
                isPostponed = true,
                comment = "Потрібно доробити trunk."
            )
            addLabIfNotExists(
                networksId,
                "ЛР2. Статична маршрутизація",
                "Налаштування маршрутів між підмережами."
            )

            // Програмування мобільних додатків
            addLabIfNotExists(
                mobAppsId,
                "ЛР1. Compose UI",
                "Списки, картки, навігація."
            )
            addLabIfNotExists(
                mobAppsId,
                "ЛР2. Room Database",
                "Створення локальної БД.",
                inProgress = true
            )

            // Бази даних
            addLabIfNotExists(
                dbId,
                "ЛР1. SQL JOIN",
                "Практика SELECT + JOIN.",
                isCompleted = true
            )
            addLabIfNotExists(
                dbId,
                "ЛР2. Нормалізація",
                "Приведення до 3НФ."
            )
        }
    }
}
