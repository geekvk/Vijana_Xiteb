package com.example.vijana_xiteb.domain.repository

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope

interface UserRepository {
    suspend fun signInWithGoogle(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
        login: (FirebaseUser) -> Unit
    ) : FirebaseUser?

    suspend fun signInWithFacebook(
        lcallbackManager: CallbackManager,
        loginButton: LoginButton,
        loginManager: LoginManager,
        context: Context
    ) : FirebaseUser?
}