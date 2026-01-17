package com.example.ta_ppid.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nama")
    val nama: String?,

    @field:SerializedName("foto")
    val foto: String?,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("role")
    val role: String
)