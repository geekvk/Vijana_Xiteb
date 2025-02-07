package com.example.vijana_xiteb.data.remote.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FacebookSignInService @Inject constructor() {
    suspend fun signIn(
        callbackManager: CallbackManager,
        loginButton: LoginButton,
        loginManager: LoginManager,
        context: Context
    ): FirebaseUser? {
        return suspendCoroutine { continuation ->
            loginManager.logInWithReadPermissions(
                context as Activity,
                listOf("email", "public_profile")
            )
            loginManager.registerCallback(
                callbackManager,
                object : com.facebook.FacebookCallback<com.facebook.login.LoginResult> {
                    override fun onCancel() {
                        Log.d("FacebookLogin", "Login canceled")
                        continuation.resume(null)
                    }

                    override fun onError(error: FacebookException) {
                        Log.d("FacebookLogin", "${error.message}")
                        continuation.resume(null)
                    }

                    override fun onSuccess(result: LoginResult) {
                        val accessToken = result.accessToken
                        Log.d("token->", accessToken.token.toString())
                        val credentials = FacebookAuthProvider.getCredential(accessToken.token)
                        Log.d("credentials->", credentials.toString())

                        FirebaseAuth.getInstance().signInWithCredential(credentials)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Successful login
                                    val user = FirebaseAuth.getInstance().currentUser
                                    Log.d("FacebookAuth", "Firebase User: ${user?.displayName}")
                                    continuation.resume(user)
                                } else if (task.exception is FirebaseAuthUserCollisionException) {
                                    // Handle account collision (email already exists)
                                    val exception = task.exception as FirebaseAuthUserCollisionException
                                    val existingEmail = exception.email

                                    if (existingEmail != null) {
                                        // Prompt the user to sign in with the existing provider
                                        // and link the Facebook account
                                        val authCredential = FacebookAuthProvider.getCredential(accessToken.token)
                                        linkAccounts(existingEmail, authCredential, continuation)
                                    } else {
                                        Log.e("FacebookAuth", "Email collision but no email provided")
                                        continuation.resume(null)
                                    }
                                } else {
                                    // Other errors
                                    Log.e("FacebookAuth", "Firebase login failed", task.exception)
                                    continuation.resume(null)
                                }
                            }
                    }
                })
        }
    }
    private fun linkAccounts(
        existingEmail: String,
        credential: AuthCredential,
        continuation: Continuation<FirebaseUser?>
    ) {
        val auth = FirebaseAuth.getInstance()
        val providers = auth.fetchSignInMethodsForEmail(existingEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (signInMethods.isNullOrEmpty()) {
                        Log.e("FacebookAuth", "No sign-in methods found for email: $existingEmail")
                        continuation.resume(null)
                        return@addOnCompleteListener
                    }

                    val provider = when {
                        signInMethods.contains(GoogleAuthProvider.GOOGLE_SIGN_IN_METHOD) -> "Google"
                        signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD) -> "Email/Password"
                        else -> null
                    }

                    if (provider != null) {
                        Log.d("FacebookAuth", "Please sign in with your $provider account to link with Facebook")
                        continuation.resume(null)
                    } else {
                        Log.e("FacebookAuth", "Unsupported provider for email: $existingEmail")
                        continuation.resume(null)
                    }
                } else {
                    Log.e("FacebookAuth", "Failed to fetch sign-in methods", task.exception)
                    continuation.resume(null)
                }
            }
    }
}