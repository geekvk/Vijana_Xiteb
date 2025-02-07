package com.example.vijana_xiteb.presentation.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.example.vijana_xiteb.data.local.UserPreferences
import com.example.vijana_xiteb.presentation.viewmodel.LoginViewModel
import com.example.vijana_xiteb.utils.Routes
import com.facebook.login.widget.LoginButton
import com.example.vijana_xiteb.R
import com.example.vijana_xiteb.utils.ScreenState

@Composable
fun LoginScreen(
    navController: NavController,
    callbackManager: CallbackManager
){
    val context = LocalContext.current

    val viewModel = hiltViewModel<LoginViewModel>()

    val fbState by viewModel.fbState.collectAsState()
    val gState by viewModel.gState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(context, "Google Sign-In Successful", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
        }
    }

    val loginButton = remember { LoginButton(context) }

    val facebookLoginLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        callbackManager.onActivityResult(result.resultCode, result.resultCode, result.data)
    }
    val loginManager = remember {
        LoginManager.getInstance()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 20.dp)
        ) {
            Text(
                text = "Explore Now",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Join Us Today",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .padding(vertical = 5.dp, horizontal = 20.dp)
                    .clickable {
                        viewModel.signInWithGoogle(launcher, login = { user ->
                            val userPreferences = UserPreferences(context)
                            userPreferences.saveUserData(
                                userId = user.uid,
                                userName = user.displayName,
                                userEmail = user.email,
                                profilePictureUrl = user.photoUrl.toString(),
                                provider = "google"
                            )
                            Toast
                                .makeText(context, "Logged in", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("Logged in", user.displayName ?: "empty")
                            navController.navigate(Routes.HOME_SCREEN) {
                                popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                                launchSingleTop = true
                            }
                        })
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ){
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier
                        .width(65.dp)
                        .height(65.dp)
                        .padding(end = 20.dp)
                )
                Text(text = "Google signIn")


            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .padding(vertical = 5.dp, horizontal = 20.dp)
                    .clickable {
                        viewModel.signInWithFacebook(
                            callbackManager,
                            loginButton,
                            loginManager,
                            context
                        ){user ->
                            Log.d("user->", user.displayName.toString())
                            val userPreferences = UserPreferences(context)
                            userPreferences.saveUserData(
                                userId = user.uid,
                                userName = user.displayName,
                                userEmail = user.email,
                                profilePictureUrl = user.photoUrl.toString(),
                                provider = "facebook"
                            )
                            Toast
                                .makeText(context, "Logged in", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("Logged in", user.displayName ?: "empty")
                            navController.navigate(Routes.HOME_SCREEN) {
                                popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                                launchSingleTop = true
                            }

                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .width(65.dp)
                        .height(65.dp)
                        .padding(end = 20.dp)
                )
                Text(text = "Facebook signIn")


            }
        }
    }
}

