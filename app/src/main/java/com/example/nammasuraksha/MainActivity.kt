package com.example.nammasuraksha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.nammasuraksha.Navigation.AppNavigation
import com.example.nammasuraksha.ui.theme.NammaSurakshaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NammaSurakshaTheme {
                AppNavigation()
            }
        }
    }
}

