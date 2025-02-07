package com.example.vijana_xiteb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    val id : String,
    val userName : String,
    val email : String,
    val profilePictureUrl : String?,
    val provider : String
)
