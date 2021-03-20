package com.example.scanall.scanAllList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanall.ProductDetail.ProductDetailActivity
import com.example.scanall.model.Produit
import com.example.scanall.viewmodel.ProduitViewModel
import com.example.scanall.databinding.ActivityProduitBinding

//Permet d'afficher la liste des produits
class ProduitActivity : AppCompatActivity(),OnItemClickListener {
    //affichage de nos produits
    //initialisation de notre viewModels
    private lateinit var binding: ActivityProduitBinding
    private  lateinit var adapter: ProduitAdapter

    //viewmodel
    private lateinit var mProduitViewModel: ProduitViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProduitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Affiche le backButton
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Produit")

        var recyclerview = binding.produitRecyclerview
        //abonnement sur les évenements de notre viewModels
        adapter = ProduitAdapter(listOf(),this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        mProduitViewModel = ViewModelProvider(this).get(ProduitViewModel::class.java)
        mProduitViewModel.readAllData.observe(this, Observer { Produit -> adapter.updateDataSet(Produit) })


    }

    private fun productUpdated(Produit: List<Produit>) {
        //creation d'une fuction pour mettre à jour nos datas
        adapter.updateDataSet(Produit)

    }

    //permet de naviguer au click d'un item
    override fun onItemClick(produit: Produit, position: Int) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        //intent.putExtra("name", dataProduit.produitName)
        intent.putExtra("code", produit.code)
        intent.putExtra("imageURL", produit.image_front_url)
        intent.putExtra("nom", produit.brands)
        intent.putExtra("ingredient", produit.ingredients_text)
        intent.putExtra("countrie", produit.countries_lc)
        intent.putExtra("date", produit.date_scan)
        startActivity(intent)
    }

    //Gère le retour au backButton
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}