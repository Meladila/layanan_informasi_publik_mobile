package com.example.ta_ppid.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ta_ppid.R
import com.example.ta_ppid.databinding.ActivityMainBinding
import com.example.ta_ppid.model.Berita
import com.example.ta_ppid.ui.home.HomeFragment

class MainActivity : AppCompatActivity(), HomeFragment.OnNavigationListener, HomeFragment.OnBeritaClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- SETUP NAVIGATION CONTROLLER ---
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Setup ActionBar dengan NavController (opsional, jika Anda menggunakan ActionBar)
        // val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.aktivitasFragment, R.id.daftarinformasiFragment, R.id.kontakFragment))
        // setupActionBarWithNavController(navController, appBarConfiguration)

        // --- LISTENER UNTUK MENYEMBUNYIKAN/MENAMPILKAN BOTTOM MENU ---
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.beritaFragment,
                R.id.galeriFragment,
                R.id.detailberitaFragment -> {
                    binding.bottomMenu.visibility = View.GONE
                }
                else -> {
                    binding.bottomMenu.visibility = View.VISIBLE
                }
            }
        }

        // --- KONFIGURASI CHIP NAVIGATION BAR ---
        // Set item default
        binding.bottomMenu.setItemSelected(R.id.home, true)

        // Sync ChipNavigationBar dengan NavController
        binding.bottomMenu.setOnItemSelectedListener { itemId ->
            when (itemId) {
                R.id.home -> navController.navigate(R.id.homeFragment)
                R.id.aktivitas -> navController.navigate(R.id.aktivitasFragment)
                R.id.daftarinformasi -> navController.navigate(R.id.daftarinformasiFragment)
                R.id.kontak -> navController.navigate(R.id.kontakFragment)
            }
            true // Kembalikan true untuk menandakan item telah diproses
        }
    }

    // --- IMPLEMENTASI METHOD DARI INTERFACE `OnNavigationListener` ---
    override fun onNavigateToBerita() {
        // Gunakan NavController untuk berpindah ke BeritaFragment
        navController.navigate(R.id.beritaFragment)
    }

    override fun onNavigateToGaleri() {
        navController.navigate(R.id.galeriFragment)
    }

    // --- IMPLEMENTASI METHOD DARI INTERFACE `OnBeritaClickListener` ---
    override fun onBeritaClicked(berita: Berita) {
        // 1. Buat Bundle untuk membawa data
        val bundle = Bundle()
        bundle.putParcelable("EXTRA_BERITA", berita)

        // 2. Navigasi ke DetailBeritaFragment sambil membawa data (bundle)
        // Ganti `action_homeFragment_to_detailberitaFragment` dengan ID action yang ada di nav_graph Anda
        navController.navigate(R.id.action_homeFragment_to_detailberitaFragment, bundle)
    }

    // Opsional: Handle tombol back agar bekerja dengan baik dengan NavController
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}