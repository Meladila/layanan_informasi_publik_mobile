package com.example.ta_ppid.ui.permohonan

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ta_ppid.R
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class PermohonanActivity : AppCompatActivity() {

    private lateinit var spinnerJenisPemohon: Spinner
    private lateinit var editTanggal: TextInputEditText
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_permohonan)

        // Inset handling (biar tidak ketutup status bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.card_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // === Inisialisasi View ===
        spinnerJenisPemohon = findViewById(R.id.spinner_jenis_pemohon)
        editTanggal = findViewById(R.id.edit_tanggal_permohonan)

        // === 1. ISI DROPDOWN JENIS PEMOHON ===
        val jenisPemohonList = listOf(
            "Perorangan",
            "Kelompok Orang",
            "Badan Hukum",
            "Lembaga Publik"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            jenisPemohonList
        )
        spinnerJenisPemohon.adapter = adapter

        // Tambahkan listener (opsional)
        spinnerJenisPemohon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = jenisPemohonList[position]
                Toast.makeText(this@PermohonanActivity, "Dipilih: $selected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // === 2. HANDLE PEMILIHAN TANGGAL ===
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
                editTanggal.setText(dateFormat.format(calendar.time))
            }

        editTanggal.setOnClickListener {
            DatePickerDialog(
                this@PermohonanActivity,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // === 3. TOMBOL SUBMIT (opsional contoh) ===
        val btnSubmit = findViewById<Button>(R.id.submit_button)
        btnSubmit.setOnClickListener {
            val jenisPemohon = spinnerJenisPemohon.selectedItem.toString()
            val tanggal = editTanggal.text.toString()

            Toast.makeText(
                this,
                "Permohonan dikirim!\nJenis: $jenisPemohon\nTanggal: $tanggal",
                Toast.LENGTH_LONG
            ).show()
        }

        // === 4. Tombol Batal ===
        findViewById<Button>(R.id.btn_batal).setOnClickListener {
            finish() // kembali ke halaman sebelumnya
        }
    }

    // Fungsi tombol back dari XML
    fun onBack(view: View) {
        finish()
    }
}
