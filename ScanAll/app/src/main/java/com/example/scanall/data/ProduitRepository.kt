package com.example.scanall.data

import androidx.lifecycle.LiveData

class ProduitRepository(private  val produitDao: ProduitDao) {

    val readAllData: LiveData<List<DataProduit>> = produitDao.readAllData()

    //fun to add produit
    suspend  fun addProduits(produit: DataProduit){
        produitDao.addProduit(produit)
    }

    //delet produit
    suspend fun deleteProduitDao(produit: DataProduit){
        produitDao.deleteProduit(produit)
    }
}