package com.lab3.data

data class Place(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

val places = listOf(
    Place(
        id = 1,
        title = "Львів — Площа Ринок",
        description = "Історичний центр Львова.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9f/Market_Square_Lviv.jpg"
    ),
    Place(
        id = 2,
        title = "Кам'янець-Подільська фортеця",
        description = "Стара українська фортеця.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/6/62/Kamenets_Podolsky_Castle.jpg"
    ),
    Place(
        id = 3,
        title = "Одеський оперний театр",
        description = "Один із найгарніших театрів Європи.",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0f/Odesa_Opera_2017.jpg"
    )
)
