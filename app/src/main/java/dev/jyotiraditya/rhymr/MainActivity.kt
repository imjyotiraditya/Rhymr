package dev.jyotiraditya.rhymr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dev.jyotiraditya.rhymr.data.permission.PermissionsManager
import dev.jyotiraditya.rhymr.presentation.navigation.PredictiveBackHandler
import dev.jyotiraditya.rhymr.presentation.screens.MainScreen
import dev.jyotiraditya.rhymr.presentation.theme.RhymrTheme

class MainActivity : ComponentActivity() {
    private lateinit var permissionsManager: PermissionsManager
    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        permissionsManager = PermissionsManager(this)
        if (!permissionsManager.arePermissionsGranted()) {
            permissionsLauncher.launch(permissionsManager.requiredPermissions)
        }

        setContent {
            val navController = rememberNavController()
            val backHandler = remember { PredictiveBackHandler(this, navController) }

            RhymrTheme {
                backHandler.HandleBackPress {
                    MainScreen(navController = navController)
                }
            }
        }
    }
}