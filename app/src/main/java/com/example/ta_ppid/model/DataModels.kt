package com.example.ta_ppid.model

import android.os.Parcelable
import com.example.ta_ppid.R
import com.example.ta_ppid.data.remote.response.Penulis
import kotlinx.parcelize.Parcelize

data class UserData(
    val nama: String,
    val username: String,
    val email: String,
    val fotoUrl: String?
)
data class Layanan(
    val icon: Int,
    val nama: String
)

@Parcelize // <--- TAMBAHKAN INI
data class Berita(
    val id: Int,
    val judul: String,
    val deskripsi: String,
    val tglPosting: String,
    val gambar: String,
    val penulis: String,
    val createdAt: String
) : Parcelable

data class Galeri(
    val imageResId: Int,
    val title: String
)

data class InformasiPublik(
    val judul: String,
    val penulis: String,
    val tanggal: String,
    val linkDownload: String
)

data class Notifikasi(
    val id: Int,
    val judul: String,
    val pesan: String,
    val waktu: String,
    val isRead: Boolean = false,
    val tipe: String // "permohonan", "keberatan", "pengaduan", "sistem"
)

sealed class Aktivitas {
    // Properti umum yang dimiliki semua jenis aktivitas
    abstract val layanan: String
    abstract val status: String
    abstract val tanggal: String
    abstract val tujuan: String
    abstract val iconResId: Int // ID resource untuk ikon

    // Data class untuk Permohonan
    data class Permohonan(
        val id: Int,
        override val layanan: String,
        override val status: String,
        override val tanggal: String,
        override val tujuan: String,
        override val iconResId: Int = R.drawable.ic_service_icon1 // Ganti dengan ikon yang sesuai
    ) : Aktivitas()

    // Data class untuk Keberatan
    data class Keberatan(
        val id: Int,
        override val layanan: String,
        override val status: String,
        override val tanggal: String,
        override val tujuan: String,
        override val iconResId: Int = R.drawable.ic_service_icon2 // Ganti dengan ikon yang sesuai
    ) : Aktivitas()

    // Data class untuk Pengaduan
    data class Pengaduan(
        val id: Int,
        override val layanan: String,
        override val status: String,
        override val tanggal: String,
        override val tujuan: String,
        override val iconResId: Int = R.drawable.ic_service_icon3 // Ganti dengan ikon yang sesuai
    ) : Aktivitas()
}