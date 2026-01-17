package com.example.ta_ppid.ui.notifikasi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_ppid.adapter.NotifikasiAdapter
import com.example.ta_ppid.databinding.ActivityNotifikasiBinding
import com.example.ta_ppid.model.Notifikasi

class NotifikasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifikasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Siapkan data dummy
        val notifikasiList = listOf(
            Notifikasi(1, "Permohonan Disetujui", "Nomor: 001/PPID/SMKN2BSK/2025", "10 Oktober 2025, 14:30", false, "permohonan"),
            Notifikasi(2, "Keberatan Diproses", "Nomor: KEB001/KEB/PPID/2025", "9 Oktober 2025, 10:15", true, "keberatan"),
            Notifikasi(3, "Pengaduan Diterima", "Laporan Anda sedang diverifikasi", "8 Oktober 2025, 16:45", false, "pengaduan")
        )

        // Setup RecyclerView
        val adapter = NotifikasiAdapter(notifikasiList)
        binding.rvNotifikasi.layoutManager = LinearLayoutManager(this)
        binding.rvNotifikasi.adapter = adapter

        // Tampilkan/sembunyikan placeholder
        if (notifikasiList.isEmpty()) {
            binding.layoutEmpty.visibility = android.view.View.VISIBLE
        } else {
            binding.layoutEmpty.visibility = android.view.View.GONE
        }
    }
}