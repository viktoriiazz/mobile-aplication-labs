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

/**
 * Lab4Database - the main database class
 * - extends on RoomDatabase()
 * - marked with @Database annotation for generating communication interfaces
 * - in annotation are added all your entities (tables)
 * - includes abstract properties of all DAO interfaces for each entity (table)
 */
@Database(entities = [SubjectEntity::class, SubjectLabEntity::class], version = 1)
abstract class Lab4Database : RoomDatabase() {
    //DAO properties for each entity (table)
    // must be abstract (because Room will generate instances by itself)
    abstract val subjectsDao: SubjectDao
    abstract val subjectLabsDao: SubjectLabsDao
}

/**
 * DatabaseStorage - custom class where you initialize and store Lab4Database single instance
 *
 */
object DatabaseStorage {
    // ! Important - all operations with DB must be done from non-UI thread!
    // coroutineScope: CoroutineScope - is the scope which allows to run asynchronous operations
    // > we will learn it soon! For now just put it here
    private val coroutineScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        },
    )

    // single instance of Lab4Database
    private var _database: Lab4Database? = null

    /**
        Function of initializing and getting Lab4Database instance
        - is invoked from place where DB should be used (from Compose screens)
        [context] - context from Compose screen to init DB
    */
    fun getDatabase(context: Context): Lab4Database {
        // if _database already contains Lab4Database instance, return this instance
        if (_database != null) return _database as Lab4Database
        // if not, create instance, preload some data and return this instance
        else {
            // creating Lab4Database instance by builder
            _database = Room.databaseBuilder(
                context,
                Lab4Database::class.java, "lab4Database"
            ).build()

            // preloading some data to DB
            preloadData()

            return _database as Lab4Database
        }
    }

    /**
        Function for preloading some initial data to DB
     */
    private fun preloadData() {
        // List of subjects
        val listOfSubject = listOf(
            SubjectEntity(title = "Subject 1"),
            SubjectEntity(title = "Subject 2"),
            SubjectEntity(title = "Subject 3"),
            SubjectEntity(title = "Subject 4"),
            SubjectEntity(title = "Subject 5"),
        )
        // List of labs
        val listOfSubjectLabs = listOf(
            SubjectLabEntity(
                subjectId = 1,
                title = "Lab[1] title",
                description = "Lab[1] description",
                comment = "Lab[1] comment",
                isCompleted = true,
            ),
            SubjectLabEntity(
                subjectId = 1,
                title = "Lab[2] title",
                description = "Lab[2] description",
                inProgress = true,
            ),
            SubjectLabEntity(
                subjectId = 1,
                title = "Lab[3] title",
                description = "Lab[3] description",
            ),
            SubjectLabEntity(
                subjectId = 3,
                title = "Lab[4] title",
                description = "Lab[4] description",
                comment = "Lab[4] comment"
            ),
        )

        // Request to add all Subjects from the list to DB
        listOfSubject.forEach { subject ->
            // coroutineScope.launch{...} - start small thread where you can make query to DB
            coroutineScope.launch {
                // INSERT query to add Subject (subjectsDao is used)
                _database?.subjectsDao?.addSubject(subject)
            }
        }
        // Request to add all Labs from the list to DB
        listOfSubjectLabs.forEach { lab ->
            coroutineScope.launch {
                // INSERT query to add Lab (subjectLabsDao is used)
                _database?.subjectLabsDao?.addSubjectLab(lab)
            }
        }
    }
}