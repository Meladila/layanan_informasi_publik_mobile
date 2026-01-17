package com.example.ta_ppid.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: Data
)

data class Data(
    @field:SerializedName("user")
    val user: UserResponse,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("token_type")
    val tokenType: String
)