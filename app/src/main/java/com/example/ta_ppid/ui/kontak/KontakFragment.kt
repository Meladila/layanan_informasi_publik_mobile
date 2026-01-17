package com.example.ta_ppid.ui.kontak

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ta_ppid.R
import com.example.ta_ppid.databinding.FragmentKontakBinding

class KontakFragment : Fragment() {

    private var _binding: FragmentKontakBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKontakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Atur aksi klik
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Aksi klik tombol kembali
        binding.iconKembali.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Aksi klik pada card alamat (buka peta)
        binding.cardAlamat.setOnClickListener {
            val address = getString(R.string.kontak_alamat).replace("Alamat: ", "")
            val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        // Aksi klik pada card email (buka aplikasi email)
        binding.cardEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:info@kemenparekraf.go.id")
            }
            startActivity(Intent.createChooser(emailIntent, "Kirim Email"))
        }

        // Aksi klik pada card website (buka browser)
        binding.cardNoTelepon.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.kontak_website)))
            startActivity(websiteIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}