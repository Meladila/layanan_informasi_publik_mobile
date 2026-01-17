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
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    // 1. Deklarasi View di level kelas
    private lateinit var editTextUsername: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextPasswordConfirmation: TextInputEditText
    private lateinit var buttonRegister: MaterialButton
    private lateinit var textViewSignIn: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // 2. Inisialisasi View
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextPasswordConfirmation = findViewById(R.id.editTextPasswordConfirmation) // Asumsi Anda punya field ini
        buttonRegister = findViewById(R.id.buttonRegister)
        textViewSignIn = findViewById(R.id.tv_sign_in)
        progressBar = findViewById(R.id.progressBar)

        // 3. Aksi saat tombol Daftar diklik
        buttonRegister.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val passwordConfirmation = editTextPasswordConfirmation.text.toString().trim()

            // Panggil fungsi register
            performRegister(username, email, password, passwordConfirmation)
        }

        // Aksi saat link Masuk diklik
        textViewSignIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun performRegister(username: String, email: String, password: String, passwordConfirmation: String) {
        // Validasi sederhana di sisi client
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != passwordConfirmation) {
            Toast.makeText(this, "Password dan Konfirmasi Password tidak cocok", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.register(username, email, password, passwordConfirmation)

                if (response.message.contains("Registrasi berhasil", ignoreCase = true)) {
                    Toast.makeText(this@RegisterActivity, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

                    // Langsung pindah ke MainActivity setelah registrasi sukses
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                // Error validasi dari server (contoh: 422 Unprocessable Entity)
                if (e.code() == 422) {
                    // Anda bisa parsing error body untuk pesan yang lebih spesifik
                    Toast.makeText(this@RegisterActivity, "Email atau Username sudah digunakan!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                Toast.makeText(this@RegisterActivity, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}