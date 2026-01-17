package com.example.ta_ppid.ui.aktivitas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_ppid.adapter.AktivitasAdapter
import com.example.ta_ppid.databinding.FragmentAktivitasBinding
import com.example.ta_ppid.model.Aktivitas
import com.example.ta_ppid.ui.keberatan.DetailKeberatanActivity
import com.example.ta_ppid.ui.pengaduan.DetailPengaduanActivity
// --- PERUBAHAN: Tambahkan import untuk Activity tujuan ---
import com.example.ta_ppid.ui.permohonan.DetailPermohonanActivity

class AktivitasFragment : Fragment() {

    private var _binding: FragmentAktivitasBinding? = null
    private val binding get() = _binding!!

    private lateinit var aktivitasAdapter: AktivitasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAktivitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val dummyData = getDummyData()

        // --- PERUBAHAN: Inisialisasi adapter dengan listener untuk klik ---
        aktivitasAdapter = AktivitasAdapter(dummyData) { aktivitas ->
            // Kode ini akan dijalankan saat item diklik
            when (aktivitas) {
                is Aktivitas.Permohonan -> {
                    // Jika yang diklik adalah Permohonan, buka DetailPermohonanActivity
                    val intent = Intent(requireContext(), DetailPermohonanActivity::class.java)
                    // Kirim ID permohonan ke activity tujuan
                    intent.putExtra("EXTRA_PERMOHONAN_ID", aktivitas.id)
                    startActivity(intent)
                }
                is Aktivitas.Keberatan -> {
                    // --- PERUBAHAN: Ganti Toast dengan Intent ---
                    val intent = Intent(requireContext(), DetailKeberatanActivity::class.java)
                    // Kirim ID keberatan ke activity tujuan
                    intent.putExtra("EXTRA_KEBERATAN_ID", aktivitas.id)
                    startActivity(intent)
                }

                is Aktivitas.Pengaduan -> {
                    // --- PERUBAHAN: Ganti Toast dengan Intent ---
                    val intent = Intent(requireContext(), DetailPengaduanActivity::class.java)
                    // Kirim ID keberatan ke activity tujuan
                    intent.putExtra("EXTRA_PENGADUAN_ID", aktivitas.id)
                    startActivity(intent)
                }
            }
        }

        // Atur RecyclerView
        binding.rvAktivitas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = aktivitasAdapter
        }
    }

    private fun getDummyData(): List<Aktivitas> {
        return listOf(
            Aktivitas.Permohonan(
                id = 101,
                layanan = "Permohonan Informasi - KTP",
                status = "Selesai",
                tanggal = "14 Oktober 2023, 10:30 WIB",
                tujuan = "Tujuan permohonan: Memperbarui data penduduk setelah menikah."
            ),
            Aktivitas.Keberatan(
                id = 201,
                layanan = "Keberatan atas Permohonan [ID: 123]",
                status = "Diproses",
                tanggal = "12 Oktober 2023, 14:15 WIB",
                tujuan = "Alasan keberatan: Informasi yang diberikan tidak sesuai dengan yang diminta."
            ),
            Aktivitas.Pengaduan(
                id = 301,
                layanan = "Pengaduan Pelayanan",
                status = "Menunggu",
                tanggal = "10 Oktober 2023, 09:00 WIB",
                tujuan = "Topik: Pengaduan tentang lamanya proses antrian di loket."
            ),
            Aktivitas.Permohonan(
                id = 102,
                layanan = "Permohonan Informasi - KK",
                status = "Ditolak",
                tanggal = "05 Oktober 2023, 11:20 WIB",
                tujuan = "Tujuan permohonan: Membuat Kartu Keluarga baru."
            ),
            Aktivitas.Pengaduan(
                id = 302,
                layanan = "Pengaduan Online",
                status = "Selesai",
                tanggal = "01 Oktober 2023, 08:00 WIB",
                tujuan = "Topik: Keluhan sulitnya akses website pendaftaran online."
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Hindari memory leak
    }
}