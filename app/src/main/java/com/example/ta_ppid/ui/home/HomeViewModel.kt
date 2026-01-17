package com.example.ta_ppid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ta_ppid.data.remote.response.Berita
import com.example.ta_ppid.data.remote.response.BeritaListResponse
import com.example.ta_ppid.data.repository.BeritaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel() {
    private val repository = BeritaRepository()

    private val _beritaList = MutableLiveData<List<Berita>>()
    val beritaList: LiveData<List<Berita>> = _beritaList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchBeritaList() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getBeritaList()
                _beritaList.value = response.data
                _isLoading.value = false
            } catch (e: HttpException) {
                _errorMessage.value = "Error: ${e.code()} - ${e.message()}"
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
                _isLoading.value = false
            }
        }
    }
}