package com.example.scanall.data

import androidx.lifecycle.LiveData
import com.example.scanall.model.Produit

class ProduitRepository(private  val produitDao: ProduitDao) {

    val readAllData: LiveData<List<Produit>> = produitDao.readAllData()

    //fun to add produit
    suspend  fun addProduits(produit: Produit){
        produitDao.addProduit(produit)
    }

    //delet produit
    suspend fun deleteProduitDao(produit: Produit){
        produitDao.addProduit(produit)
    }
}