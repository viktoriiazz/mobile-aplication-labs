package com.lab3.ui.screens.placeDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lab3.data.ItemsData
import com.lab3.ui.navigation.PlaceListDetailsRoute
import com.lab3.ui.navigation.PlaceListRoute

/**
 * PlaceDetailsScreen - the second screen in app with the details of selected item
 * - [route]: PlaceListDetailsRoute - the NavKey parameter which includes id to find and show corresponding item on screen
 */
@Composable
fun PlaceDetailsScreen(
    route: PlaceListDetailsRoute,
) {
    // State with the item
    val itemState = remember {
        mutableStateOf(
            // Finding the item by id in shared data source ItemsData.itemsList
            ItemsData.itemsList.first { it.id == route.id }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Id: ${itemState.value.id}", modifier = Modifier.fillMaxWidth())
            Text(text = "Title: ${itemState.value.title}", modifier = Modifier.fillMaxWidth())
            Text(
                text = "Description: ${itemState.value.description}", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceDetailsScreenPreview() {
    PlaceDetailsScreen(
        route = PlaceListDetailsRoute(id = 3)
    )
}