package com.example.ta_ppid.data.repository

import com.example.ta_ppid.data.remote.ApiClient
import com.example.ta_ppid.data.remote.response.BeritaDetailResponse
import com.example.ta_ppid.data.remote.response.BeritaListResponse

class BeritaRepository {
    private val apiService = ApiClient.beritaInstance

    suspend fun getBeritaList(): BeritaListResponse {
        return apiService.getBeritaList()
    }

    suspend fun getBeritaDetail(id: Int): BeritaDetailResponse {
        return apiService.getBeritaDetail(id)
    }
}