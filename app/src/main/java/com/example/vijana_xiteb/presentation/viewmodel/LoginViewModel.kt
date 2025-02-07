package com.example.vijana_xiteb.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vijana_xiteb.domain.usecase.UserUseCase
import com.example.vijana_xiteb.utils.ScreenState
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUserCase: UserUseCase
) : ViewModel() {
    private val _fbState = MutableStateFlow<ScreenState<FirebaseUser>>(ScreenState.Loading)
    val fbState: StateFlow<ScreenState<FirebaseUser>> = _fbState

    private val _gState = MutableStateFlow<ScreenState<FirebaseUser>>(ScreenState.Loading)
    val gState: StateFlow<ScreenState<FirebaseUser>> = _gState

    fun signInWithGoogle(
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
        login: (FirebaseUser) -> Unit
    ){

        viewModelScope.launch(Dispatchers.IO) {
            try{
                val user = userUserCase.googleSignIn(launcher, login)
                user?.let {
                    _gState.value = (ScreenState.Success(user))
                    Log.d("Googles User", user.toString())
                }
            }catch (e : Exception){
                _gState.value = (ScreenState.Error(e.message ?: "Unknown error"))
            }
        }

    }

    fun signInWithFacebook(
        callbackManager: CallbackManager,
        loginButton: LoginButton,
        loginManager: LoginManager,
        context: Context,
        onSuccess : (FirebaseUser) -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                Log.d("facebook->", "Facevook")
                val user = userUserCase.facebookSignIn(callbackManager, loginButton, loginManager, context)
                user?.let {
                    _fbState.value = (ScreenState.Success(user))
                    Log.d("FB USER", user.toString())
                    onSuccess(it)
                }
            }catch (e : Exception){
                _fbState.value = (ScreenState.Error(e.message ?: "Unknown error"))
            }
        }

    }
}