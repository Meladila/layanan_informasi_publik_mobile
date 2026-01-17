package com.example.ta_ppid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_ppid.R
import com.example.ta_ppid.databinding.RawAktivitasBinding
import com.example.ta_ppid.model.Aktivitas

class AktivitasAdapter(
    private var aktivitasList: List<Aktivitas>,
    private val onItemClickListener: (Aktivitas) -> Unit
) : RecyclerView.Adapter<AktivitasAdapter.AktivitasViewHolder>() {

    // Menggunakan View Binding untuk akses view yang lebih mudah dan aman
    class AktivitasViewHolder(val binding: RawAktivitasBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AktivitasViewHolder {
        val binding = RawAktivitasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AktivitasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AktivitasViewHolder, position: Int) {
        val aktivitas = aktivitasList[position]
        holder.binding.apply {
            // Set data ke view
            tvLayanan.text = aktivitas.layanan
            tvStatus.text = aktivitas.status
            tvTanggalPermohonan.text = aktivitas.tanggal
            tvTujuan.text = aktivitas.tujuan
            imgLogo.setImageResource(aktivitas.iconResId)

            // Atur warna background status berdasarkan teks status
            val context = holder.itemView.context
            val statusBackground = when (aktivitas.status.lowercase()) {
                "berhasil", "selesai", "dikabulkan" -> R.drawable.shape_status_success
                "diproses", "menunggu_kelengkapan", "diteruskan_ppid" -> R.drawable.shape_status_warning
                "diajukan", "menunggu" -> R.drawable.shape_status_info
                "ditolak" -> R.drawable.shape_status_danger
                else -> R.drawable.shape_status_info
            }
            tvStatus.setBackgroundResource(statusBackground)

            root.setOnClickListener {
                // Panggil listener yang diberikan dari Fragment
                // 'onItemClickListener' adalah fungsi lambda yang kita tambahkan di constructor
                onItemClickListener(aktivitas)
            }
        }
    }

    override fun getItemCount(): Int = aktivitasList.size

    // Fungsi untuk memperbarui data
    fun updateData(newAktivitasList: List<Aktivitas>) {
        this.aktivitasList = newAktivitasList
        notifyDataSetChanged()
    }

}