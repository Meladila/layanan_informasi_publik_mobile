package com.example.ta_ppid.data.remote.response

import com.google.gson.annotations.SerializedName

// Ini adalah pembungkus untuk respons daftar berita
data class BeritaListResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: List<Berita>
)

// Ini adalah pembungkus untuk respons detail berita
data class BeritaDetailResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: Berita
)