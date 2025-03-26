package dev.jyotiraditya.rhymr.presentation.navigation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import dev.jyotiraditya.rhymr.presentation.navigation.Route
import dev.jyotiraditya.rhymr.presentation.navigation.navigationItems

@Composable
fun RhymrBottomNav(
    navController: NavHostController,
    currentRoute: Route?,
    onTabSelected: (String) -> Unit = {}
) {
    NavigationBar {
        navigationItems.forEach { item ->
            val isSelected = currentRoute?.path == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onTabSelected(item.route)

                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.label)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.label),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}