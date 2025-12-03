package com.plater.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.presentation.navigation.PlateRNavGraph
import com.plater.android.presentation.uiresources.PlateRTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity of the PlateR application.
 * Sets up the Compose UI with edge-to-edge display and navigation graph.
 *
 * @property userPreferencesManager Injected UserPreferencesManager for managing user preferences
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesManager: UserPreferencesManager

    /**
     * Initializes the activity, enables edge-to-edge display, and sets up the Compose UI.
     *
     * @param savedInstanceState Previously saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlateRTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlateRNavGraph(
                        modifier = Modifier.padding(innerPadding),
                        userPreferencesManager = userPreferencesManager
                    )
                }

            }
        }
    }
}