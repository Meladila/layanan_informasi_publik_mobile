package com.example.ta_ppid.ui.profile

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ta_ppid.R
import com.example.ta_ppid.databinding.ActivityProfileBinding
import com.example.ta_ppid.model.UserData

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 👇 Siapkan data dummy (ganti dengan data sesungguhnya nanti)
        val userData = UserData(
            nama = "Melani Adila Putri",
            username = "Melani",
            email = "melani@example.com",
            fotoUrl = null // atau URL gambar jika ada
        )

        // Tampilkan data ke UI
        showProfileData(userData)

        // Set klik tombol kembali
        binding.btnBack.setOnClickListener {
            finish() // kembali ke activity sebelumnya
        }
    }

    private fun showProfileData(user: UserData) {
        binding.tvNama.text = user.nama
        binding.tvUsername.text = "@${user.username}"
        binding.tvUsernameDetail.text = user.username
        binding.tvEmail.text = user.email

        // Load foto profil
        if (!user.fotoUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(user.fotoUrl)
                .placeholder(R.drawable.ic_person)
                .into(binding.ivFotoProfil)
        } else {
            binding.ivFotoProfil.setImageResource(R.drawable.ic_person)
        }
    }

    // Fungsi onBack tetap ada untuk kompatibilitas XML (opsional)
    fun onBack(view: View) {
        finish()
    }
}