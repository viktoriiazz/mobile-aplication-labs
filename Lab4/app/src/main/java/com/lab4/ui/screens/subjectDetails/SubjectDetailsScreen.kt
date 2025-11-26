package com.lab4.ui.screens.subjectDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab4.data.db.DatabaseStorage
import com.lab4.data.entity.SubjectEntity
import com.lab4.data.entity.SubjectLabEntity
import com.lab4.ui.navigation.SubjectDetailsRoute
import com.lab4.ui.theme.Lab4Theme

@Composable
fun SubjectDetailsScreen(
    route: SubjectDetailsRoute,
) {
    // Context - object which contains info about your app, has access to storage
    // is used for Room DB initialization
    val context = LocalContext.current
    // Getting the DB instance for screen
    val db = DatabaseStorage.getDatabase(context)

    // States have to be filled with values from LaunchedEffect(Unit) {}
    val subjectState = remember { mutableStateOf<SubjectEntity?>(null) }
    val subjectLabsState = remember { mutableStateOf<List<SubjectLabEntity>>(emptyList()) }

    /** ! Important !
        LaunchEffect(){...} - is the side effect which allows make operations in another thread (for accessing to DB)
     *  This example LaunchEffect(Unit) - means that the operations inside will be performed only once on creating the screen
     */
    LaunchedEffect(Unit) {
        // fetching the Subject from DB by id (subjectsDao is used)
        subjectState.value = db.subjectsDao.getSubjectById(route.id)
        // fetching the Labs from DB by subject id (subjectLabsDao is used)
        subjectLabsState.value = db.subjectLabsDao.getSubjectLabsBySubjectId(route.id)
    }

    // UI of screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Subject", fontSize = 28.sp)
        Text(
            text = "ID: ${subjectState.value?.id} Title: ${subjectState.value?.title}",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(text = "Labs", fontSize = 28.sp, modifier = Modifier.padding(top = 16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            items(subjectLabsState.value) { lab ->
                Surface(
                    shadowElevation = 8.dp,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Lab: ID:${lab.id} Subject:${lab.subjectId} Title: ${lab.title}",
                            fontSize = 20.sp
                        )
                        Text(
                            text = "isCompleted:${lab.isCompleted} | isInProgress:${lab.inProgress}",
                            fontSize = 20.sp
                        )
                        Text(text = "description:${lab.description}", fontSize = 16.sp)
                        Text(text = "comment:${lab.comment}", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

/**
 * Preview can't display data from DB
 */
@Preview(showBackground = true)
@Composable
private fun SubjectDetailsScreenPreview() {
    Lab4Theme {
        SubjectDetailsScreen(SubjectDetailsRoute(1))
    }
}