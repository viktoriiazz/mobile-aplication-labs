package com.lab3.ui.screens.placeDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.lab3.data.places
import com.lab3.ui.navigation.PlaceListDetailsRoute
import androidx.navigation3.runtime.NavBackStack

@Composable
fun PlaceDetailsScreen(
    route: PlaceListDetailsRoute,
    backStack: NavBackStack<*>
) {
    val place = places.first { it.id == route.id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Кнопка назад
        Button(onClick = {
            if (backStack.isNotEmpty()) {
                backStack.removeAt(backStack.lastIndex)
            }
        }) {
            Text("Назад")
        }

        Spacer(Modifier.height(16.dp))

        Image(
            painter = rememberAsyncImagePainter(place.imageUrl),
            contentDescription = place.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        Text(place.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text(place.description, style = MaterialTheme.typography.bodyLarge)
    }
}
