package com.example.vijana_xiteb.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.vijana_xiteb.utils.Constants

class UserPreferences(
    context : Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
    companion object {
        const val USER_ID_KEY = "user_id"
        const val USER_NAME_KEY = "user_name"
        const val USER_EMAIL_KEY = "user_email"
        const val USER_PROFILE_PIC_KEY = "user_profile_picture_url"
        const val PROVIDER_KEY = "provider"
    }
    fun saveUserData(userId: String, userName: String?, userEmail: String?, profilePictureUrl: String?, provider : String?) {
        sharedPreferences.edit().apply {
            putString(USER_ID_KEY, userId)
            putString(USER_NAME_KEY, userName)
            putString(USER_EMAIL_KEY, userEmail)
            putString(USER_PROFILE_PIC_KEY, profilePictureUrl)
            putString(PROVIDER_KEY, provider)
            apply()
        }
    }

    fun getUserData(): User {
        val userId = sharedPreferences.getString(USER_ID_KEY, "") ?: ""
        val userName = sharedPreferences.getString(USER_NAME_KEY, "") ?: ""
        val userEmail = sharedPreferences.getString(USER_EMAIL_KEY, "") ?: ""
        val userProfilePictureUrl = sharedPreferences.getString(USER_PROFILE_PIC_KEY, "") ?: ""
        val provider = sharedPreferences.getString(PROVIDER_KEY, "") ?: ""
        return User(
            id = userId,
            userName,
            email = userEmail,
            profilePictureUrl = userProfilePictureUrl,
            provider

            )
    }
    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }
}