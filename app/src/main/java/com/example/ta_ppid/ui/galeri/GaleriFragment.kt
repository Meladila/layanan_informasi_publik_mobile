package com.example.ta_ppid.ui.galeri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ta_ppid.R
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ta_ppid.adapter.GaleriAdapter
import com.example.ta_ppid.databinding.FragmentGaleriBinding
import com.example.ta_ppid.model.Galeri

class GaleriFragment : Fragment() {

    private var _binding: FragmentGaleriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGaleriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val dummyGaleri = listOf(
            Galeri(R.drawable.ic_news_image1, "Kegiatan 1"),
            Galeri(R.drawable.ic_news_image1, "Kegiatan 2"),
            Galeri(R.drawable.ic_news_image1, "Kegiatan 3"),
            Galeri(R.drawable.ic_news_image1, "Kegiatan 4"),
            Galeri(R.drawable.ic_news_image1, "Kegiatan 5"),
            Galeri(R.drawable.ic_news_image1, "Kegiatan 6")
        )

        val adapter = GaleriAdapter(dummyGaleri)
        binding.rvGaleriFull.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(requireContext(), 2) // Grid 2 kolom
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}