package com.example.ta_ppid.ui.berita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_ppid.R
import com.example.ta_ppid.adapter.BeritaAdapter
import com.example.ta_ppid.data.remote.response.Berita // Pastikan import ini benar

class BeritaFragment : Fragment() {

    private lateinit var beritaViewModel: BeritaViewModel
    private lateinit var beritaAdapter: BeritaAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyLayout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_berita, container, false)

        recyclerView = view.findViewById(R.id.rvBeritaList)
        progressBar = view.findViewById(R.id.progressBar)
        emptyLayout = view.findViewById(R.id.layout_empty_berita)

        setupRecyclerView()
        setupViewModel()

        // Handle back button
        view.findViewById<View>(R.id.btn_back).setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    private fun setupRecyclerView() {
        // PERBAIKAN: Tambahkan tipe eksplisit ": Berita" pada parameter lambda
        beritaAdapter = BeritaAdapter(emptyList()) { berita: Berita ->
            // Navigate ke detail berita dengan membawa ID
            val bundle = Bundle().apply {
                putInt("beritaId", berita.id)
            }
            findNavController().navigate(R.id.action_navigation_berita_to_navigation_detail_berita, bundle)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = beritaAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel() {
        beritaViewModel = ViewModelProvider(requireActivity())[BeritaViewModel::class.java]

        beritaViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (isLoading) {
                recyclerView.visibility = View.GONE
                emptyLayout.visibility = View.GONE
            } else {
                // Pastikan RecyclerView muncul saat loading selesai, meskipun data kosong
                recyclerView.visibility = View.VISIBLE
            }
        }

        beritaViewModel.beritaList.observe(viewLifecycleOwner) { beritaList ->
            if (beritaList.isEmpty()) {
                recyclerView.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
                // Gunakan metode updateData() untuk mengupdate adapter
                beritaAdapter.updateData(beritaList)
            }
        }

        beritaViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }

        // Load berita data
        beritaViewModel.fetchBeritaList()
    }
}