package com.example.ta_ppid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ta_ppid.R
import com.example.ta_ppid.data.remote.response.Berita
import com.example.ta_ppid.data.remote.response.Penulis
import java.text.SimpleDateFormat
import java.util.*

class BeritaAdapter(
    private var beritaList: List<Berita>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(berita: Berita)
    }

    fun updateData(newBeritaList: List<Berita>) {
        beritaList = newBeritaList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.raw_berita, parent, false)
        return BeritaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        holder.bind(beritaList[position])
    }

    override fun getItemCount(): Int = beritaList.size

    inner class BeritaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivBerita: ImageView = itemView.findViewById(R.id.iv_berita_image)
        private val tvJudul: TextView = itemView.findViewById(R.id.tv_berita_title)
        private val tvPenulis: TextView = itemView.findViewById(R.id.tv_berita_author)
        private val tvTanggal: TextView = itemView.findViewById(R.id.tv_berita_date)

        fun bind(berita: Berita) {
            tvJudul.text = berita.judul
            tvPenulis.text = "Oleh: ${berita.penulis.name}"

            // Format tanggal
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val date = inputFormat.parse(berita.tglPosting)
                tvTanggal.text = date?.let { outputFormat.format(it) } ?: berita.tglPosting
            } catch (e: Exception) {
                tvTanggal.text = berita.tglPosting
            }

            // Load image with Glide
            Glide.with(itemView.context)
                .load(berita.gambar)
                .placeholder(R.drawable.ic_news_image1)
                .error(R.drawable.ic_news_image1)
                .centerCrop()
                .into(ivBerita)

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(berita)
            }
        }
    }
}