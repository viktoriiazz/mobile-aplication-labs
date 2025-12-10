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
import kotlinx.coroutines.launch

@Composable
fun SubjectDetailsScreen(
    route: SubjectDetailsRoute,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val db = DatabaseStorage.getDatabase(context)

    val subject = remember { mutableStateOf<SubjectEntity?>(null) }
    val labs = remember { mutableStateOf<List<SubjectLabEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        subject.value = db.subjectsDao.getSubjectById(route.id)
        labs.value = db.subjectLabsDao.getSubjectLabsBySubjectId(route.id)
    }

    Column(Modifier.padding(16.dp)) {

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
            subject.value?.title ?: "",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Лабораторні роботи",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        // LIST OF LABS
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {

            items(labs.value, key = { it.id!! }) { lab ->

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

                        // STATUS COLOR
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
                                statusText,
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
                                updateStatus(db, lab.id!!, false, true, false, route.id, labs)
                            }
                            StatusBtn("⏳ У процесі") {
                                updateStatus(db, lab.id!!, true, false, false, route.id, labs)
                            }
                            StatusBtn("❗ Відкладено") {
                                updateStatus(db, lab.id!!, false, false, true, route.id, labs)
                            }
                            StatusBtn("❌ Скинути") {
                                updateStatus(db, lab.id!!, false, false, false, route.id, labs)
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
                                CoroutineScope(Dispatchers.IO).launch {
                                    db.subjectLabsDao.updateComment(lab.id!!, commentText)
                                    labs.value =
                                        db.subjectLabsDao.getSubjectLabsBySubjectId(route.id)
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

fun updateStatus(
    db: Lab4Database,
    labId: Int,
    inProgress: Boolean,
    isCompleted: Boolean,
    isPostponed: Boolean,
    subjectId: Int,
    labs: MutableState<List<SubjectLabEntity>>
) {
    CoroutineScope(Dispatchers.IO).launch {
        db.subjectLabsDao.updateStatus(labId, inProgress, isCompleted, isPostponed)
        labs.value = db.subjectLabsDao.getSubjectLabsBySubjectId(subjectId)
    }
}
