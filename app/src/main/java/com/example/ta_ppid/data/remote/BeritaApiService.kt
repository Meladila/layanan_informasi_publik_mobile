package com.example.ta_ppid.data.remote

import com.example.ta_ppid.data.remote.response.BeritaListResponse
import com.example.ta_ppid.data.remote.response.BeritaDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BeritaApiService {
    // Endpoint untuk mendapatkan daftar semua berita
    // PERBAIKAN: Mengembalikan objek pembungkus BeritaListResponse
    @GET("api/berita")
    suspend fun getBeritaList(): BeritaListResponse

    // Endpoint untuk mendapatkan detail satu berita berdasarkan ID
    // PERBAIKAN: Mengembalikan objek pembungkus BeritaDetailResponse
    // dan tipe id diubah menjadi Int
    @GET("api/berita/{id}")
    suspend fun getBeritaDetail(@Path("id") id: Int): BeritaDetailResponse
}