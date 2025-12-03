package com.lab3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.lab3.ui.screens.placeDetails.PlaceDetailsScreen
import com.lab3.ui.screens.placesList.PlacesListScreen
import kotlinx.serialization.Serializable

@Serializable
data object PlaceListRoute : NavKey

@Serializable
data class PlaceListDetailsRoute(val id: Int) : NavKey

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(PlaceListRoute)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = entryProvider {

            // Головний екран
            entry<PlaceListRoute> {
                PlacesListScreen(
                    onDetailsScreen = { id ->
                        backStack.add(PlaceListDetailsRoute(id))
                    }
                )
            }

            // Екран деталей — ТУТ ПЕРЕДАЄМО backStack
            entry<PlaceListDetailsRoute> { route ->
                PlaceDetailsScreen(
                    route = route,
                    backStack = backStack
                )
            }
        }
    )
}
