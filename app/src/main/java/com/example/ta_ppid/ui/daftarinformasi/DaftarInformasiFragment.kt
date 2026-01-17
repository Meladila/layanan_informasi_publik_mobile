package com.example.ta_ppid.ui.daftarinformasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ta_ppid.adapter.InformasiPublikAdapter // Import adapter
import com.example.ta_ppid.databinding.FragmentDaftarinformasiBinding
import com.example.ta_ppid.model.InformasiPublik // Import model

class DaftarInformasiFragment : Fragment() {

    // Menggunakan View Binding
    private var _binding: FragmentDaftarinformasiBinding? = null
    private val binding get() = _binding!!

    // Deklarasi Adapter
    private lateinit var informasiAdapter: InformasiPublikAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarinformasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Siapkan data contoh (dummy data)
        setupDummyData()

        // 2. Inisialisasi dan atur RecyclerView
        setupRecyclerView()

        // 3. Atur aksi klik pada tombol back
        binding.ivBack.setOnClickListener {
            // Kembali ke fragment sebelumnya
            requireActivity().onBackPressed()
        }
    }

    private fun setupDummyData() {
        val listInformasi = listOf(
            InformasiPublik(
                "Laporan Tahunan PPID 2024",
                "Admin",
                "30 Oktober 2025",
                "http://example.com/laporan_2024.pdf"
            ),
            InformasiPublik(
                "Daftar Informasi Publik Berkala",
                "Admin",
                "28 Oktober 2025",
                "http://example.com/daftar_berkala.pdf"
            ),
            InformasiPublik(
                "Standar Layanan Publik",
                "Operator",
                "25 Oktober 2025",
                "http://example.com/standar_layanan.pdf"
            ),
            InformasiPublik(
                "Prosedur Permohonan Informasi Publik",
                "Admin",
                "20 Oktober 2025",
                "http://example.com/prosedur_permohonan.pdf"
            )
        )

        // Buat instance adapter dengan data
        informasiAdapter = InformasiPublikAdapter(listInformasi)
    }

    private fun setupRecyclerView() {
        // Atur RecyclerView
        binding.rvInformasiPublik.apply {
            // BENAR: Gunakan 'layoutManager =' untuk menetapkan
            layoutManager = LinearLayoutManager(context)

            // BENAR: Gunakan 'adapter =' untuk menetapkan
            adapter = informasiAdapter
        }

        // Atur aksi klik
        informasiAdapter.setOnItemClickListener(object : InformasiPublikAdapter.OnItemClickListener {
            override fun onItemClick(informasi: InformasiPublik) {
                Toast.makeText(requireContext(), "Klik: ${informasi.judul}", Toast.LENGTH_SHORT).show()
            }

            override fun onDownloadClick(informasi: InformasiPublik) {
                Toast.makeText(requireContext(), "Mengunduh: ${informasi.judul}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Hindari memory leak
    }
}