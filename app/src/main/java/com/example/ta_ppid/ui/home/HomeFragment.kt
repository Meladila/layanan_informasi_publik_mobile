package com.example.ta_ppid.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_ppid.R
import com.example.ta_ppid.adapter.BeritaAdapter
import com.example.ta_ppid.adapter.GaleriAdapter
import com.example.ta_ppid.adapter.LayananAdapter
import com.example.ta_ppid.databinding.FragmentHomeBinding
import com.example.ta_ppid.model.Berita
import com.example.ta_ppid.model.Galeri
import com.example.ta_ppid.model.Layanan
import com.example.ta_ppid.ui.keberatan.KeberatanActivity
import com.example.ta_ppid.ui.notifikasi.NotifikasiActivity
import com.example.ta_ppid.ui.pengaduan.PengaduanActivity
import com.example.ta_ppid.ui.permohonan.PermohonanActivity
import com.example.ta_ppid.ui.profile.ProfileActivity

class HomeFragment : Fragment(), LayananAdapter.OnItemClickListener, BeritaAdapter.OnItemClickListener {

    interface OnNavigationListener {
        fun onNavigateToBerita()
        fun onNavigateToGaleri()
    }

    // Interface baru untuk komunikasi klik berita ke Activity
    interface OnBeritaClickListener {
        fun onBeritaClicked(beritaId: Int) // Kirim ID saja
    }


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var layananAdapter: LayananAdapter
    private lateinit var beritaAdapter: BeritaAdapter
    private lateinit var galeriAdapter: GaleriAdapter

    // Tambahkan ViewModel
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayananData()
        setupGaleriData()
        setupLayananRecyclerView()
        setupBeritaRecyclerView()
        setupGaleriRecyclerView()
        observeViewModel()

        // Hubungkan adapter dengan listener
        layananAdapter.setOnItemClickListener(this)
        beritaAdapter.setOnItemClickListener(this)

        binding.notificationImageView.setOnClickListener {
            val intent = Intent(requireContext(), NotifikasiActivity::class.java)
            startActivity(intent)
        }

        binding.profileImageView.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        beritaAdapter.setOnItemClickListener(object : BeritaAdapter.OnItemClickListener {
            override fun onItemClick(berita: Berita) {
                // Kirim hanya ID-nya
                (activity as? OnBeritaClickListener)?.onBeritaClicked(berita.id)
            }
        })


        binding.tvLihatGaleri.setOnClickListener {
            (activity as? OnNavigationListener)?.onNavigateToGaleri()
        }
    }

    private fun setupLayananData() {
        val listLayanan = listOf(
            Layanan(R.drawable.ic_service_icon1, "Permohonan"),
            Layanan(R.drawable.ic_service_icon2, "Keberatan"),
            Layanan(R.drawable.ic_service_icon3, "Pengaduan")
        )
        layananAdapter = LayananAdapter(listLayanan)
    }

    private fun setupGaleriData() {
        val listGaleri = listOf(
            Galeri(R.drawable.ic_news_image1, "Kegiatan Sosialisasi PPID"),
            Galeri(R.drawable.ic_news_image1, "Kunjungan Kerja Dinas"),
            Galeri(R.drawable.ic_news_image1, "Pelatihan Internal"),
            Galeri(R.drawable.ic_news_image1, "Hari Keterbukaan Informasi")
        )
        galeriAdapter = GaleriAdapter(listGaleri)
    }

    private fun setupLayananRecyclerView() {
        binding.rvLayanan.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLayanan.adapter = layananAdapter
    }

    private fun setupBeritaRecyclerView() {
        // Inisialisasi adapter dengan daftar kosong
        beritaAdapter = BeritaAdapter(emptyList(), this)
        binding.rvBerita.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBerita.adapter = beritaAdapter
    }

    private fun setupGaleriRecyclerView() {
        binding.rvGaleri.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGaleri.adapter = galeriAdapter
    }

    private fun observeViewModel() {
        // Observasi data berita dari ViewModel
        homeViewModel.beritaList.observe(viewLifecycleOwner) { beritaList ->
            // Tampilkan hanya 3 berita pertama
            val limitedList = if (beritaList.size > 3) beritaList.take(3) else beritaList
            beritaAdapter.updateData(limitedList)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Tampilkan/sembunyikan loading indicator jika diperlukan
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // Tampilkan pesan error jika diperlukan
        }

        // Fetch data berita dari API
        homeViewModel.fetchBeritaList()
    }

    // IMPLEMENTASIKAN FUNGSI DARI KONTRAK (INTERFACE)
    override fun onItemClick(layanan: Layanan) {
        when (layanan.nama) {
            "Permohonan" -> {
                val intent = Intent(requireContext(), PermohonanActivity::class.java)
                startActivity(intent)
            }
            "Keberatan" -> {
                val intent = Intent(requireContext(), KeberatanActivity::class.java)
                startActivity(intent)
            }
            "Pengaduan" -> {
                val intent = Intent(requireContext(), PengaduanActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // Implementasi klik untuk BERITA (memberi tahu Activity)
    override fun onItemClick(berita: Berita) {
        // Kirim ID berita ke Activity
        (activity as? OnBeritaClickListener)?.onBeritaClicked(berita.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}