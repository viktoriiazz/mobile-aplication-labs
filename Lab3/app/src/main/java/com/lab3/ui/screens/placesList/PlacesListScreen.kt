package com.lab3.ui.screens.placesList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.lab3.data.Place
import com.lab3.data.places

@Composable
fun PlacesListScreen(
    onDetailsScreen: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(places) { place ->
            PlaceItem(place = place, onClick = { onDetailsScreen(place.id) })
            Spacer(Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaceItem(place: Place, onClick: () -> Unit) {
    Card(
        onClick = onClick,         // ← ЦЕ ВАЖЛИВО! Без clickable()
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(place.imageUrl),
                contentDescription = place.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(place.title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(6.dp))
                Text(place.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
