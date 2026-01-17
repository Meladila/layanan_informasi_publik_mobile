package com.example.ta_ppid.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ta_ppid.R
import com.example.ta_ppid.data.remote.ApiClient
import com.example.ta_ppid.ui.main.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {

    // 1. Pindahkan semua deklarasi View ke level kelas
    private lateinit var editTextUsername: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: MaterialButton
    private lateinit var textViewSignUp: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // 2. Inisialisasi View di onCreate (tanpa 'val')
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewSignUp = findViewById(R.id.tv_sign_up)
        progressBar = findViewById(R.id.progressBar)

        // 3. Atur aksi saat tombol Masuk diklik
        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Validasi input sederhana
            if (username.isEmpty()) {
                editTextUsername.error = "Username tidak boleh kosong"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                editTextPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            // Panggil fungsi login
            performLogin(username, password)
        }

        // Aksi saat link Daftar diklik
        textViewSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin(username: String, password: String) {
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // Panggil API login
                val response = ApiClient.instance.login(username, password)

                // Jika server merespon sukses (kode 2xx)
                if (response.message == "Login berhasil") {
                    Toast.makeText(this@LoginActivity, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                    // Pindah ke MainActivity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Hancurkan LoginActivity
                } else {
                    // Blok ini akan dijalankan jika server mengembalikan kode 200 OK
                    // tetapi dengan pesan error di dalam body JSON.
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Gagal: ${response.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                // --- PERUBAHAN UTAMA ADA DI SINI ---
                // Blok catch akan menangkap error jaringan atau error HTTP (seperti 401, 404, 500)

                if (e is HttpException) {
                    // Periksa kode status HTTP
                    when (e.code()) {
                        401 -> {
                            // Jika kode 401 (Unauthorized), artinya username/password salah
                            Toast.makeText(
                                this@LoginActivity,
                                "Username atau Password yang Anda masukkan salah.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            // Untuk error HTTP lainnya
                            Toast.makeText(
                                this@LoginActivity,
                                "Terjadi kesalahan server: ${e.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    // Untuk error lainnya (misal: tidak ada koneksi internet)
                    Toast.makeText(
                        this@LoginActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } finally {
                // Sembunyikan ProgressBar
                progressBar.visibility = View.GONE
            }
        }
    }
}