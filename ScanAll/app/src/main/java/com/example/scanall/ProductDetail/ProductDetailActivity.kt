package com.example.scanall.ProductDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.viewmodel.savedstate.R
import com.bumptech.glide.Glide
import com.example.scanall.databinding.ActivityProductDetailBinding
import com.example.scanall.databinding.ActivityProduitBinding
import kotlinx.android.synthetic.main.activity_product_detail.*

//Permet d'afficher les details d'un produit
class ProductDetailActivity : AppCompatActivity() {

    //on crée notre variable binding pour la view
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Affiche le backButton
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Détail")

        //Affectation des données a nos items
        binding.textViewCode.text="code: ${getIntent().getStringExtra("code")}"
        Glide.with(binding.imageView2).load(getIntent().getStringExtra("imageURL")).into(binding.imageView2)
        binding.textViewBrands.text = "nom: ${getIntent().getStringExtra("nom")}"
        binding.textViewIngredientText.text="${getIntent().getStringExtra("ingredient")}"
        binding.textViewCountrie.text="countrie: ${getIntent().getStringExtra("countrie")}"
        binding.textViewDateScan.text="date scan: ${getIntent().getStringExtra("date")}"
        binding.textView8.text="description"
    }

    //Gère le retour au backButton
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}