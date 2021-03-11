package com.example.scanall.scanAllList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanall.Produit
import com.example.scanall.R
import com.example.scanall.databinding.ActivityMainBinding
import com.example.scanall.databinding.ActivityProduitBinding
import com.example.scanall.databinding.ItemProduitBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class ProduitActivity : AppCompatActivity() {
    //affichage de nos produits
    //initialisation de notre viewModels
    private val model: ScanAllListViewModel by viewModels()
    private lateinit var binding: ActivityProduitBinding
    private  lateinit var adapter: ProduitAdapter
    /*private val products = listOf<Produit>(
        Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
        Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
        Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
        Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
        Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
        Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
        Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link")
    )*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProduitBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //abonnement sur les évenements de notre viewModels
        model.getProduitLiveData().observe(this, Observer { products -> productUpdated(products!!)})
        //utilisation de notre adapter
        //adapter = ProduitAdapter(products)

        adapter = ProduitAdapter(listOf())

        binding.produitRecyclerview.adapter = adapter
        binding.produitRecyclerview.layoutManager = LinearLayoutManager(this)

        //chargement de nos données
        model.loadProduit()

    }

    private fun productUpdated(produits: List<Produit>) {
        //creation d'une fuction pour mettre à jour nos datas
        adapter.updateDataSet(produits)

    }
}