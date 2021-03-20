package com.example.scanall.scanAllList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanall.ProfileDetail
import com.example.scanall.data.DataProduit
import com.example.scanall.data.ProduitViewModel
import com.example.scanall.databinding.ActivityProduitBinding
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class ProduitActivity : AppCompatActivity(), onClickItem {
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

        var recyclerview = binding.produitRecyclerview
        //abonnement sur les évenements de notre viewModels
        adapter = ProduitAdapter(listOf(), this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        mProduitViewModel = ViewModelProvider(this).get(ProduitViewModel::class.java)
        mProduitViewModel.readAllData.observe(this, Observer { dataProduit -> adapter.updateDataSet(dataProduit) })

    }
    private fun productUpdated(dataProduit: List<DataProduit>) {
        //creation d'une fuction pour mettre à jour nos datas
        adapter.updateDataSet(dataProduit)

    }

    override fun onClick(dataProduit: DataProduit, position: Int) {
        //Toast.makeText(this, "the message ${dataProduit.produitCodeBare.toString()}", Toast.LENGTH_LONG).show()
        Log.i("ProduitActivity", "onClick: the message ${dataProduit.produitCodeBare}")
        val intent = Intent(this, ProfileDetail::class.java)
        //intent.putExtra("name", dataProduit.produitName)
        intent.putExtra("description", dataProduit.produitDescription)
        intent.putExtra("code", dataProduit.produitCodeBare)
        intent.putExtra("ingredients", dataProduit.produitIngredients)
        startActivity(intent)
    }


}