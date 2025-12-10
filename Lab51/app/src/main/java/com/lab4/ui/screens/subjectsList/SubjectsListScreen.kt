package com.lab4.ui.screens.subjectsList

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab4.data.db.DatabaseStorage
import com.lab4.data.entity.SubjectEntity

@Composable
fun SubjectsListScreen(
    onDetailsScreen: (Int) -> Unit,
) {
    val context = LocalContext.current
    val db = DatabaseStorage.getDatabase(context)
    val subjectsListState = remember { mutableStateOf<List<SubjectEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        subjectsListState.value = db.subjectsDao.getAllSubjects()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Мої предмети цього семестру",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(subjectsListState.value) { subject ->
                Surface(
                    tonalElevation = 4.dp,
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable(
                            interactionSource = null,
                            indication = LocalIndication.current,
                        ) {
                            subject.id?.let { id -> onDetailsScreen(id) }
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = subject.title,
                            fontSize = 22.sp
                        )
                        Text(
                            text = "Натисніть, щоб переглянути лабораторні роботи",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
