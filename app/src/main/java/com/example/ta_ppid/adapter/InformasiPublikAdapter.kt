package com.example.ta_ppid.adapter // Pastikan package-nya benar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_ppid.R
import com.example.ta_ppid.model.InformasiPublik

class InformasiPublikAdapter(private val listInformasi: List<InformasiPublik>) :
    RecyclerView.Adapter<InformasiPublikAdapter.InformasiViewHolder>() {

    // Interface untuk menangani klik pada item
    interface OnItemClickListener {
        fun onItemClick(informasi: InformasiPublik)
        fun onDownloadClick(informasi: InformasiPublik)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // ViewHolder menyimpan referensi ke view di dalam item layout (raw_daftarinformasipublik.xml)
    class InformasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJudul: TextView = itemView.findViewById(R.id.tv_judul)
        val tvAuthor: TextView = itemView.findViewById(R.id.author)
        val tvTanggal: TextView = itemView.findViewById(R.id.tanggal)
        val btnDownload: ImageButton = itemView.findViewById(R.id.btn_download)
    }

    // Dipanggil saat RecyclerView butuh membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformasiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.raw_daftarinformasipublik, parent, false)
        return InformasiViewHolder(view)
    }

    // Dipanggil untuk menghubungkan data dengan ViewHolder
    override fun onBindViewHolder(holder: InformasiViewHolder, position: Int) {
        val currentInformasi = listInformasi[position]

        // Set data ke view
        holder.tvJudul.text = currentInformasi.judul
        holder.tvAuthor.text = currentInformasi.penulis
        holder.tvTanggal.text = currentInformasi.tanggal

        // Atur aksi klik pada seluruh item
        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentInformasi)
        }

        // Atur aksi klik khusus pada tombol download
        holder.btnDownload.setOnClickListener {
            listener?.onDownloadClick(currentInformasi)
        }
    }

    // Mengembalikan jumlah total item dalam list
    override fun getItemCount(): Int {
        return listInformasi.size
    }
}