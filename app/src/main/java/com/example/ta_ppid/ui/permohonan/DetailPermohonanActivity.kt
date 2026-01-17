package com.example.ta_ppid.ui.permohonan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_ppid.databinding.ActivityDetailPermohonanBinding


class DetailPermohonanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPermohonanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPermohonanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        }
}