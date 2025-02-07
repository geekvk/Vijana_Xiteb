package com.example.vijana_xiteb.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vijana_xiteb.presentation.screens.HomeScreen
import com.example.vijana_xiteb.presentation.screens.LoginScreen
import com.example.vijana_xiteb.ui.theme.Vijana_XitebTheme
import com.example.vijana_xiteb.utils.Routes
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
        enableEdgeToEdge()
        setContent {
            Vijana_XitebTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.LOGIN_SCREEN){
                    composable(Routes.LOGIN_SCREEN){
                        LoginScreen(navController = navController, callbackManager)
                    }
                    composable(Routes.HOME_SCREEN) {
                        HomeScreen(navController)
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
