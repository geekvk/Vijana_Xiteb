package com.example.vijana_xiteb.data.repository

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.example.vijana_xiteb.data.remote.service.FacebookSignInService
import com.example.vijana_xiteb.data.remote.service.GoogleSignInService
import com.example.vijana_xiteb.domain.repository.UserRepository
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val googleSignInService: GoogleSignInService,
    private val facebookSignInService : FacebookSignInService
) :  UserRepository {

    override suspend fun signInWithGoogle(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
        login: (FirebaseUser) -> Unit
    ): FirebaseUser? {
        return googleSignInService.signIn(launcher, login)
    }

    override suspend fun signInWithFacebook(
        callbackManager: CallbackManager,
        loginButton: LoginButton,
        loginManager: LoginManager,
        context: Context
    ): FirebaseUser? {
        return facebookSignInService.signIn(callbackManager, loginButton, loginManager, context)
    }
}