package com.example.vijana_xiteb.domain.usecase

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.example.vijana_xiteb.domain.repository.UserRepository
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository : UserRepository
) {
    suspend fun googleSignIn(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
        login: (FirebaseUser) -> Unit
    ) : FirebaseUser? {
        return userRepository.signInWithGoogle(launcher, login)
    }

    suspend fun facebookSignIn(
        callbackManager: CallbackManager,
        loginButton: LoginButton,
        loginManager: LoginManager,
        context: Context
    ) : FirebaseUser?{
        return userRepository.signInWithFacebook(callbackManager,loginButton,loginManager, context)
    }
}