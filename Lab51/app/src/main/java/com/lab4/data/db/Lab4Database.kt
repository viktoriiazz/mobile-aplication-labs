package com.lab4.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lab4.data.dao.SubjectDao
import com.lab4.data.dao.SubjectLabsDao
import com.lab4.data.entity.SubjectEntity
import com.lab4.data.entity.SubjectLabEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(
    entities = [SubjectEntity::class, SubjectLabEntity::class],
    version = 2, // 🔥 ПІДНЯЛИ ВЕРСІЮ!
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
            .fallbackToDestructiveMigration() // 🔥 ДУЖЕ ВАЖЛИВО
            .build()

        preloadData()

        return _database!!
    }

    private fun preloadData() {

        val subjects = listOf(
            SubjectEntity(title = "Кіберфізичні системи"),
            SubjectEntity(title = "Управління IT-проєктами"),
            SubjectEntity(title = "Комп’ютерні мережі"),
            SubjectEntity(title = "Програмування мобільних додатків"),
            SubjectEntity(title = "Бази даних")
        )

        val labs = listOf(

            // Кіберфізичні системи
            SubjectLabEntity(
                subjectId = 1,
                title = "ЛР1. Робота з сенсорами",
                description = "Дослідження освітлення, температури, тиску.",
                isCompleted = true,
                comment = "Гарний результат"
            ),
            SubjectLabEntity(
                subjectId = 1,
                title = "ЛР2. ESP32. Таймери та GPIO",
                description = "Таймери, переривання, робота з GPIO.",
                inProgress = true
            ),

            // Комп’ютерні мережі
            SubjectLabEntity(
                subjectId = 3,
                title = "ЛР1. VLAN конфігурація",
                description = "Trunk, access, сегментація.",
                isPostponed = true,
                comment = "Потрібно доробити trunk."
            ),
            SubjectLabEntity(
                subjectId = 3,
                title = "ЛР2. Статична маршрутизація",
                description = "Налаштування маршрутів між підмережами."
            ),

            // Мобільні додатки
            SubjectLabEntity(
                subjectId = 4,
                title = "ЛР1. Compose UI",
                description = "Списки, картки, навігація."
            ),
            SubjectLabEntity(
                subjectId = 4,
                title = "ЛР2. Room Database",
                description = "Створення локальної БД.",
                inProgress = true
            ),

            // Бази даних
            SubjectLabEntity(
                subjectId = 5,
                title = "ЛР1. SQL JOIN",
                description = "Практика SELECT + JOIN.",
                isCompleted = true
            ),
            SubjectLabEntity(
                subjectId = 5,
                title = "ЛР2. Нормалізація",
                description = "Приведення до 3НФ."
            )
        )

        subjects.forEach { subject ->
            coroutineScope.launch {
                _database?.subjectsDao?.addSubject(subject)
            }
        }

        labs.forEach { lab ->
            coroutineScope.launch {
                _database?.subjectLabsDao?.addSubjectLab(lab)
            }
        }
    }
}
