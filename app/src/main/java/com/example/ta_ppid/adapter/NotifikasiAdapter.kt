package com.example.ta_ppid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_ppid.R
import com.example.ta_ppid.model.Notifikasi

class NotifikasiAdapter(
    private val notifikasiList: List<Notifikasi>
) : RecyclerView.Adapter<NotifikasiAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.iv_icon)
        val tvJudul: TextView = view.findViewById(R.id.tv_judul)
        val tvPesan: TextView = view.findViewById(R.id.tv_pesan)
        val tvWaktu: TextView = view.findViewById(R.id.tv_waktu)
        val unreadIndicator: View = view.findViewById(R.id.view_unread_indicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notifikasi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notif = notifikasiList[position]

        // Set teks
        holder.tvJudul.text = notif.judul
        holder.tvPesan.text = notif.pesan
        holder.tvWaktu.text = notif.waktu

        // Set ikon berdasarkan tipe
        when (notif.tipe) {
            "permohonan" -> holder.ivIcon.setImageResource(R.drawable.ic_service_icon1)
            "keberatan" -> holder.ivIcon.setImageResource(R.drawable.ic_service_icon2)
            "pengaduan" -> holder.ivIcon.setImageResource(R.drawable.ic_service_icon3)
            else -> holder.ivIcon.setImageResource(R.drawable.ic_info)
        }

        // Tampilkan/hilangkan indikator belum dibaca
        holder.unreadIndicator.visibility = if (notif.isRead) View.GONE else View.VISIBLE
    }

    override fun getItemCount() = notifikasiList.size
}