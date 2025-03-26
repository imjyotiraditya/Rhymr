package dev.jyotiraditya.rhymr.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.jyotiraditya.rhymr.data.model.Track
import dev.jyotiraditya.rhymr.presentation.navigation.Route
import dev.jyotiraditya.rhymr.presentation.navigation.components.RhymrBottomNav
import dev.jyotiraditya.rhymr.presentation.navigation.components.RhymrTopBar
import dev.jyotiraditya.rhymr.presentation.screens.home.HomeScreen
import dev.jyotiraditya.rhymr.presentation.screens.lyrics.LyricsScreen
import dev.jyotiraditya.rhymr.presentation.screens.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = Route.fromPath(navBackStackEntry?.destination?.route)
    var selectedTrack by remember { mutableStateOf<Track?>(null) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RhymrTopBar(
                currentRoute = currentRoute,
                onNavigateBack = { navController.popBackStack() },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            RhymrBottomNav(
                navController = navController,
                currentRoute = currentRoute,
                onTabSelected = { tabRoute ->
                    if (tabRoute == Route.Home.path) {
                        selectedTrack = null
                        navController.navigate(Route.Home.path)
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Home.path,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Home.path) {
                HomeScreen(
                    onTrackSelected = { track ->
                        selectedTrack = track
                        navController.navigate(Route.Lyrics.path)
                    }
                )
            }
            composable(Route.Lyrics.path) {
                LyricsScreen(
                    track = selectedTrack,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Route.Settings.path) {
                SettingsScreen()
            }
        }
    }
}