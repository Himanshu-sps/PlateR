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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesManager: UserPreferencesManager

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