package com.example.ta_ppid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_ppid.model.Layanan
import com.example.ta_ppid.R

class LayananAdapter(private val listLayanan: List<Layanan>) :
    RecyclerView.Adapter<LayananAdapter.LayananViewHolder>() {

    // Interface untuk menangani klik pada item
    interface OnItemClickListener {
        fun onItemClick(layanan: Layanan)
    }

    private var listener: OnItemClickListener? = null

    // Fungsi untuk mengatur listener dari luar (misalnya dari Fragment)
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // ViewHolder menyimpan referensi ke view di dalam item layout (raw_layanan.xml)
    class LayananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconLayanan: ImageView = itemView.findViewById(R.id.service_icon)
        val titleLayanan: TextView = itemView.findViewById(R.id.service_title)
    }

    // Dipanggil saat RecyclerView butuh membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayananViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.raw_layanan, parent, false)
        return LayananViewHolder(view)
    }

    // Dipanggil untuk menghubungkan data dengan ViewHolder
    override fun onBindViewHolder(holder: LayananViewHolder, position: Int) {
        val currentLayanan = listLayanan[position]

        // Set data ke view
        holder.iconLayanan.setImageResource(currentLayanan.icon)
        holder.titleLayanan.text = currentLayanan.nama

        // Atur aksi klik
        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentLayanan)
        }
    }

    // Mengembalikan jumlah total item dalam list
    override fun getItemCount(): Int {
        return listLayanan.size
    }
}