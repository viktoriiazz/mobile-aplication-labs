package com.example.lab21_zborovska

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close // <-- ВИПРАВЛЕННЯ ПОМИЛКИ 'Close'
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab21_zborovska.ui.theme.Lab21_ZborovskaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab21_ZborovskaTheme {
                TodoListScreen()
            }
        }
    }
}

// ---------------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class) // <-- ВИПРАВЛЕННЯ ПОПЕРЕДЖЕННЯ TopAppBar
@Composable
fun TodoListScreen() {
    // Стан для списку завдань
    val tasks = remember {
        mutableStateListOf("Приклад: Купити хліб", "Приклад: Написати звіт")
    }

    // Стан для тексту, який вводиться
    var newTaskText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Простий ToDo-список") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Секція ДОДАВАННЯ ЗАВДАННЯ ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Поле введення
                OutlinedTextField(
                    value = newTaskText,
                    onValueChange = { newTaskText = it },
                    label = { Text("Нове завдання") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Кнопка "Додати завдання"
                Button(
                    onClick = {
                        if (newTaskText.isNotBlank()) {
                            tasks.add(newTaskText.trim())
                            newTaskText = ""
                        }
                    },
                    enabled = newTaskText.isNotBlank()
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Додати")
                }
            }

            // --- Секція ВІДОБРАЖЕННЯ СПИСКУ ---
            Text(
                text = "Список введених справ:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // LazyColumn для ефективного відображення списку
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(task = task, onDelete = { tasks.remove(task) })
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: String, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = task)

            // Кнопка для видалення завдання
            IconButton(onClick = onDelete) {
                // Використовуємо Icons.Filled.Close, який ми імпортували
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Видалити",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------------

@Preview(showBackground = true)
@Composable
fun TodoListPreview() {
    Lab21_ZborovskaTheme {
        TodoListScreen()
    }
}