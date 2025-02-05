package com.example.vijana_xiteb.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vijana_xiteb.presentation.screens.HomeScreen
import com.example.vijana_xiteb.presentation.screens.LoginScreen
import com.example.vijana_xiteb.ui.theme.Vijana_XitebTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Vijana_XitebTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "/Login"){
                    composable("/Login"){
                        LoginScreen()
                    }
                    composable("/Home") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}
