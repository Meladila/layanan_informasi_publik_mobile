package com.example.ta_ppid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_ppid.databinding.RawGaleriBinding // Pastikan nama binding sesuai
import com.example.ta_ppid.model.Galeri

class GaleriAdapter(private var galeriList: List<Galeri>) :
    RecyclerView.Adapter<GaleriAdapter.GaleriViewHolder>() {

    // ViewHolder untuk menyimpan referensi view
    class GaleriViewHolder(val binding: RawGaleriBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriViewHolder {
        val binding = RawGaleriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GaleriViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GaleriViewHolder, position: Int) {
        val galeri = galeriList[position]
        holder.binding.apply {
            ivGalleryImage.setImageResource(galeri.imageResId)
            tvGalleryTitle.text = galeri.title
        }
    }

    override fun getItemCount(): Int = galeriList.size

    // Fungsi untuk memperbarui data
    fun updateData(newGaleriList: List<Galeri>) {
        this.galeriList = newGaleriList
        notifyDataSetChanged()
    }
}