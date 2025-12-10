package com.lab4.ui.screens.subjectDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab4.data.db.DatabaseStorage
import com.lab4.data.db.Lab4Database
import com.lab4.data.entity.SubjectEntity
import com.lab4.data.entity.SubjectLabEntity
import com.lab4.ui.navigation.SubjectDetailsRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

@Composable
fun SubjectDetailsScreen(
    route: SubjectDetailsRoute,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val db = DatabaseStorage.getDatabase(context)

    val subjectState = remember { mutableStateOf<SubjectEntity?>(null) }
    val labsState = remember { mutableStateOf<List<SubjectLabEntity>>(emptyList()) }

    val scope = rememberCoroutineScope()


    LaunchedEffect(route.id) {
        val subjectFromDb = withContext(Dispatchers.IO) {
            db.subjectsDao.getSubjectById(route.id)
        }
        val labsFromDb = withContext(Dispatchers.IO) {
            db.subjectLabsDao.getSubjectLabsBySubjectId(route.id)
        }

        subjectState.value = subjectFromDb
        labsState.value = labsFromDb
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔙 BACK BUTTON
        Button(
            onClick = onBack,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("← Назад")
        }

        // TITLE
        Text(
            text = subjectState.value?.title ?: "",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Лабораторні роботи",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        // LIST OF LABS
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {

            items(
                items = labsState.value,
                key = { it.id ?: it.hashCode() } // ✅ стабільний key
            ) { lab ->

                // Локальний стейт для поля коментаря
                var commentText by remember(lab.id) {
                    mutableStateOf(lab.comment ?: "")
                }

                Surface(
                    tonalElevation = 2.dp,
                    shadowElevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        // LAB TITLE
                        Text(
                            text = lab.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(6.dp))

                        // STATUS COLOR & TEXT
                        val statusColor = when {
                            lab.isCompleted -> Color(0xFF4CAF50)
                            lab.inProgress -> Color(0xFFFFA000)
                            lab.isPostponed -> Color(0xFFD32F2F)
                            else -> Color.Gray
                        }

                        val statusText = when {
                            lab.isCompleted -> "Виконано ✔"
                            lab.inProgress -> "У процесі ⏳"
                            lab.isPostponed -> "Відкладено ❗"
                            else -> "Не розпочато ❌"
                        }

                        // COLORED STATUS BADGE
                        Box(
                            modifier = Modifier
                                .background(
                                    color = statusColor.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = statusText,
                                color = statusColor,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        // STATUS BUTTONS
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StatusBtn("✔ Виконано") {
                                updateStatusSafe(
                                    scope = scope,
                                    db = db,
                                    labId = lab.id!!,
                                    inProgress = false,
                                    isCompleted = true,
                                    isPostponed = false,
                                    subjectId = route.id,
                                    labsState = labsState
                                )
                            }
                            StatusBtn("⏳ У процесі") {
                                updateStatusSafe(
                                    scope = scope,
                                    db = db,
                                    labId = lab.id!!,
                                    inProgress = true,
                                    isCompleted = false,
                                    isPostponed = false,
                                    subjectId = route.id,
                                    labsState = labsState
                                )
                            }
                            StatusBtn("❗ Відкладено") {
                                updateStatusSafe(
                                    scope = scope,
                                    db = db,
                                    labId = lab.id!!,
                                    inProgress = false,
                                    isCompleted = false,
                                    isPostponed = true,
                                    subjectId = route.id,
                                    labsState = labsState
                                )
                            }
                            StatusBtn("❌ Скинути") {
                                updateStatusSafe(
                                    scope = scope,
                                    db = db,
                                    labId = lab.id!!,
                                    inProgress = false,
                                    isCompleted = false,
                                    isPostponed = false,
                                    subjectId = route.id,
                                    labsState = labsState
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        // COMMENT FIELD
                        OutlinedTextField(
                            value = commentText,
                            onValueChange = { commentText = it },
                            label = { Text("Коментар") },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        // SAVE BUTTON
                        Button(
                            onClick = {
                                scope.launch {
                                    val updatedLabs = withContext(Dispatchers.IO) {
                                        db.subjectLabsDao.updateComment(lab.id!!, commentText)
                                        db.subjectLabsDao.getSubjectLabsBySubjectId(route.id)
                                    }
                                    labsState.value = updatedLabs
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Зберегти")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBtn(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
        modifier = Modifier.height(32.dp)
    ) {
        Text(text, fontSize = 12.sp)
    }
}


fun updateStatusSafe(
    scope: CoroutineScope,
    db: Lab4Database,
    labId: Int,
    inProgress: Boolean,
    isCompleted: Boolean,
    isPostponed: Boolean,
    subjectId: Int,
    labsState: MutableState<List<SubjectLabEntity>>
) {
    scope.launch {
        val updatedLabs = withContext(Dispatchers.IO) {
            db.subjectLabsDao.updateStatus(labId, inProgress, isCompleted, isPostponed)
            db.subjectLabsDao.getSubjectLabsBySubjectId(subjectId)
        }
        labsState.value = updatedLabs
    }
}
