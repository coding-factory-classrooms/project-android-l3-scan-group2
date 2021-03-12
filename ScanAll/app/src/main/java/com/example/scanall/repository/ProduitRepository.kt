package com.example.scanall.repository

import androidx.lifecycle.LiveData
import com.example.scanall.model.Produit
import com.example.scanall.data.ProduitDao

//Permet d'ajouter des produits dans notre base de donnée
class ProduitRepository (private val produitDao: ProduitDao){

    val readAllData:LiveData<List<Produit>> = produitDao.readAllData()

    suspend fun addProduit(produit: Produit){
        produitDao.addProduit(produit)
    }
}