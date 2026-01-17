package com.example.ta_ppid.data.remote.response

import com.google.gson.annotations.SerializedName

// Nama diubah dari BeritaResponse menjadi Berita agar lebih representatif
data class Berita(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("judul")
    val judul: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("tgl_posting")
    val tglPosting: String,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("penulis")
    val penulis: Penulis,

    @field:SerializedName("created_at")
    val createdAt: String
)

// file: response/Penulis.kt
data class Penulis(
    @field:SerializedName("id")
    val id: Int,

    // PERBAIKAN: SerializedName harus "name" karena itu adalah kunci di JSON Anda
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("username")
    val username: String
)