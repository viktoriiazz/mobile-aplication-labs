@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.lab3.ui.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.lab3.ui.screens.placeDetails.PlaceDetailsScreen
import com.lab3.ui.screens.placesList.PlacesListScreen
import kotlinx.serialization.Serializable

// Constants with path (links) for screens
@Serializable
data object PlaceListRoute : NavKey

@Serializable
data class PlaceListDetailsRoute(val id: Int) : NavKey

/**
 * NavigationGraph - composable function, is just a container with navigation logic
 */

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
) {
    /**
     * backStack - the stack with navigation routes
     * - [PlaceListRoute] - the first screen which has to be shown
     */
    val backStack = rememberNavBackStack(PlaceListRoute)

    /**
     * NavDisplay - container manager for stack
     * - [backStack] - the backStack above
     * - [entryDecorators] - the default implementation of some navigation logic (e.g transition animations between screens) just copy as it is
     * - [entryProvider] - the provider which contains declarations of Route -> @Composable Screen()
     *      here you define the logic of creating the composable screens for each Route (NavKey)
     */
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            // For PlaceListRoute -> PlacesListScreen(...)
            entry<PlaceListRoute> {
                PlacesListScreen(
                    // function of screen to open new screen with details and send there parameter ID
                    onDetailsScreen = { id ->
                        backStack.add(PlaceListDetailsRoute(id))
                    }
                )
            }
            // For PlaceListDetailsRoute -> PlaceDetailsScreen(...)
            // the NavDisplay manages routes by itself
            // so... under the hood --> PlaceListDetailsRoute -> PlaceDetailsScreen(PlaceListDetailsRoute(id))
            entry<PlaceListDetailsRoute> { route -> PlaceDetailsScreen(route) }
        }
    )
}