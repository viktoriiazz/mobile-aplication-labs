package com.example.lab11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box // <-- ПОТРІБНО ДЛЯ ЦЕНТРУВАННЯ
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment // <-- ПОТРІБНО ДЛЯ ЦЕНТРУВАННЯ
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp // <-- ПОТРІБНО ДЛЯ ВІДСТУПІВ
import androidx.compose.ui.unit.sp // <-- ПОТРІБНО ДЛЯ РОЗМІРУ ТЕКСТУ
import com.example.lab11.ui.theme.Lab11Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab11Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // Змінюємо фон на світло-сірий
                    containerColor = Color.LightGray
                ) { innerPadding ->
                    // Використовуємо Box для центрування вмісту
                    Box(
                        contentAlignment = Alignment.Center, // <-- ЦЕНТРУЄМО ВСЕ ПО СЕРЕДИНІ
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Greeting(name = "Viktoriia")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        // Встановлюємо КОЛІР ТЕКСТУ на чорний
        color = Color.Black,
        // Встановлюємо РОЗМІР ТЕКСТУ (наприклад, 32sp)
        fontSize = 32.sp,
        modifier = modifier
            // Встановлюємо СВІТЛО-БЛАКИТНИЙ ФОН для прямокутника
            .background(Color.Cyan)
            // Додаємо внутрішній відступ, щоб текст не прилипав до країв фону
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab11Theme {
        Greeting(
            name = "Android",
            modifier = Modifier.background(Color.Cyan)
        )
    }
}