package com.example.scanall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.scanall.databinding.ActivityMainBinding
import com.example.scanall.scanAllList.ProduitActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //chargement du fichier correspondant à notre vue xml générer avec le viewBinding
        //pratique pour récupérer les élément de notre interface(bouton, input)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonProduit.setOnClickListener {
                //startActivity
                startActivity(Intent(this, ProduitActivity::class.java))
        }
    }
}