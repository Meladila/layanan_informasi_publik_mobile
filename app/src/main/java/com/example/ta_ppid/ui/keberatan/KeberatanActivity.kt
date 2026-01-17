package com.example.ta_ppid.ui.keberatan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_ppid.R
import com.example.ta_ppid.databinding.ActivityKeberatanBinding
import java.text.SimpleDateFormat
import java.util.*

class KeberatanActivity : AppCompatActivity() {

    // Menggunakan View Binding
    private lateinit var binding: ActivityKeberatanBinding

    // Calendar untuk date picker
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeberatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Isi data pemohon (biasanya dari data sebelumnya atau database)
        populateUserData()

        // 2. Atur Spinner untuk Alasan Keberatan
        setupSpinner()

        // 3. Atur aksi klik pada tombol dan view lainnya
        setupActions()
    }

    private fun populateUserData() {
        // Di aplikasi nyata, data ini bisa didapat dari Intent, SharedPreferences, atau ViewModel
        // Untuk contoh, kita gunakan data hardcode
        binding.tvNamaPemohon.text = "Budi Santoso"
        binding.tvAlamatPemohon.text = "Jl. Kenanga No. 10, Padang"
        binding.tvPekerjaanPemohon.text = "Pegawai Swasta"
        binding.tvNoHpPemohon.text = "081234567890"
        binding.tvIdPermohonan.text = "001/PPID/SMKN2BSK/2023"

        // Set tanggal keberatan ke hari ini
        updateDateInView()
    }

    private fun setupSpinner() {
        val alasanKeberatan = resources.getStringArray(R.array.alasan_keberatan_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, alasanKeberatan)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAlasanKeberatan.adapter = adapter
    }

    private fun setupActions() {
        // Aksi saat tombol kembali diklik
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Aksi saat TextView tanggal diklik untuk menampilkan DatePicker
        binding.tvTanggalKeberatan.setOnClickListener {
            showDatePicker()
        }

        // Aksi saat tombol Batal diklik
        binding.btnBatal.setOnClickListener {
            finish() // Tutup activity
        }

        // Aksi saat tombol Simpan diklik
        binding.btnSimpan.setOnClickListener {
            if (validateForm()) {
                // Jika validasi berhasil
                Toast.makeText(this, "Keberatan berhasil disimpan!", Toast.LENGTH_SHORT).show()
                // Di sini Anda bisa menambahkan logika untuk menyimpan data ke server atau database
                finish() // Tutup activity setelah menyimpan
            }
        }
    }

    private fun showDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // Format tanggal yang diinginkan
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.tvTanggalKeberatan.text = sdf.format(calendar.time)
    }

    private fun validateForm(): Boolean {
        val tujuanPenggunaan = binding.editTujuanPenggunaan.text.toString().trim()
        val selectedAlasan = binding.spinnerAlasanKeberatan.selectedItemPosition

        if (tujuanPenggunaan.isEmpty()) {
            Toast.makeText(this, "Tujuan penggunaan informasi harus diisi!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedAlasan == 0) { // Asumsikan item pertama adalah placeholder
            Toast.makeText(this, "Silakan pilih alasan keberatan!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    // Fungsi untuk onClick dari XML (jika Anda tetap ingin menggunakannya)
    fun onBack(view: View) {
        finish()
    }
}