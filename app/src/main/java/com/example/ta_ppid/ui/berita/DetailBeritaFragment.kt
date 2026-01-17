package com.example.ta_ppid.ui.berita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ta_ppid.R
import java.text.SimpleDateFormat
import java.util.*

class DetailBeritaFragment : Fragment() {

    private lateinit var beritaViewModel: BeritaViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var ivBeritaImage: ImageView
    private lateinit var tvBeritaTitle: TextView
    private lateinit var tvBeritaDate: TextView
    private lateinit var tvBeritaAuthor: TextView
    private lateinit var tvBeritaContent: TextView

    // Gunakan navArgs untuk menerima argumen dengan aman
    private val args: DetailBeritaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_berita, container, false)

        progressBar = view.findViewById(R.id.progressBarDetail)
        ivBeritaImage = view.findViewById(R.id.iv_detail_image)
        tvBeritaTitle = view.findViewById(R.id.tv_detail_title)
        tvBeritaDate = view.findViewById(R.id.tv_detail_date)
        tvBeritaAuthor = view.findViewById(R.id.tv_detail_author)
        tvBeritaContent = view.findViewById(R.id.tv_detail_content)

        // Handle back button
        view.findViewById<View>(R.id.btn_back).setOnClickListener {
            findNavController().navigateUp()
        }

        // Ambil ID berita dari args
        val beritaId = args.beritaId
        setupViewModel()
        beritaViewModel.fetchBeritaDetail(beritaId)

        return view
    }

    private fun setupViewModel() {
        beritaViewModel = ViewModelProvider(requireActivity())[BeritaViewModel::class.java]

        // ... observer setup tetap sama ...
        beritaViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (isLoading) {
                ivBeritaImage.visibility = View.GONE
                tvBeritaTitle.visibility = View.GONE
                tvBeritaDate.visibility = View.GONE
                tvBeritaAuthor.visibility = View.GONE
                tvBeritaContent.visibility = View.GONE
            } else {
                ivBeritaImage.visibility = View.VISIBLE
                tvBeritaTitle.visibility = View.VISIBLE
                tvBeritaDate.visibility = View.VISIBLE
                tvBeritaAuthor.visibility = View.VISIBLE
                tvBeritaContent.visibility = View.VISIBLE
            }
        }

        beritaViewModel.beritaDetail.observe(viewLifecycleOwner) { berita ->
            tvBeritaTitle.text = berita.judul
            tvBeritaContent.text = berita.deskripsi
            tvBeritaAuthor.text = berita.penulis.name

            // Format tanggal
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val date = inputFormat.parse(berita.tglPosting)
                tvBeritaDate.text = date?.let { outputFormat.format(it) } ?: berita.tglPosting
            } catch (e: Exception) {
                tvBeritaDate.text = berita.tglPosting
            }

            // Load image with Glide
            Glide.with(this)
                .load(berita.gambar)
                .placeholder(R.drawable.ic_news_image1)
                .error(R.drawable.ic_news_image1)
                .centerCrop()
                .into(ivBeritaImage)
        }

        beritaViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}