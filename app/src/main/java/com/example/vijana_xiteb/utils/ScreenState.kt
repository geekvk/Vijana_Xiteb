package com.example.vijana_xiteb.utils

sealed class ScreenState<out T> {
    data class Success<out T>(val data : T) : ScreenState<T>()
    data class Error(val message : String) : ScreenState<Nothing>()
    object Loading : ScreenState<Nothing>()

}