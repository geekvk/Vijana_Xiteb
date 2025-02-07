package com.example.vijana_xiteb.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vijana_xiteb.data.local.UserPreferences
import coil.compose.AsyncImage
import com.example.vijana_xiteb.utils.Routes

@Composable
fun HomeScreen(
    navController: NavController
){
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)

    val user = userPreferences.getUserData()

    val userName = user.userName ?: "Guest"
    val userEmail = user.email ?: "Not Available"
    val profilePicture = user.profilePictureUrl

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            profilePicture?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                        .padding(8.dp)
                )
            } ?: run {
                // If no profile picture, display a placeholder
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "No Profile Picture",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = userName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = userEmail,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Button(
                onClick = {
                    userPreferences.clearUserData()
                    navController.navigate(Routes.LOGIN_SCREEN) {
                        popUpTo(Routes.HOME_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Logout")
            }

        }
    }
}