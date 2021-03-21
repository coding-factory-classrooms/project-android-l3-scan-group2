package com.example.scanall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.findNavController
import com.example.scanall.data.DataProduit
import com.example.scanall.data.ProduitViewModel
import com.example.scanall.databinding.ActivityMainBinding
import com.example.scanall.databinding.ActivityProfileDetailBinding


class ProfileDetail : AppCompatActivity() {
    //binding
    lateinit var binding : ActivityProfileDetailBinding
    //private lateinit var mProduitViewModelView: ProduitViewModel
    lateinit var id:DataProduit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)   //on vas chercher notre layout
        setContentView(binding.root)
        binding.profileTextView.text = getIntent().getStringExtra("name")
        binding.profileDescripTextView2.text = getIntent().getStringExtra("description")
        binding.profileIngrediensTextView4.text = getIntent().getStringExtra("ingredients")
        binding.profileDateScanTextView3.text = getIntent().getStringExtra("code")
        var del = getIntent().getStringExtra("name")


        //initialisation
        binding.supprimerButton.setOnClickListener(){
            deleteProduitItem(R.id.produitCodeBareTextView)
            //findNavController().navigate(R.id.buttonList)
        }

    }

    //suppression en fonction du produit(id)
    fun deleteProduitItem(itemId: Int): Boolean{
        if(itemId==R.id.supprimerButton)
        {
        deleteProduit()
        }
        return deleteProduitItem(itemId)
    }

    private fun deleteProduit() {
    //mProduitViewModel.deleteProduitVModel()
    }
}