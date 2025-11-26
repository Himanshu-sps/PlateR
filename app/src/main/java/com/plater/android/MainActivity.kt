package com.plater.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.presentation.navigation.PlateRNavGraph
import com.plater.android.presentation.uiresources.PlateRTheme

class MainActivity : ComponentActivity() {

    private val userPreferencesManager by lazy {
        UserPreferencesManager(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlateRTheme {
                PlateRNavGraph(userPreferencesManager = userPreferencesManager)
            }
        }
    }
}