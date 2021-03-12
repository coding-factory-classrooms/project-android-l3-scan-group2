package com.example.scanall.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scanall.model.Produit


//contient les méthode pour les requêtes

@Dao
interface ProduitDao {
    //si le produit est déjà dans la base il ignore
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduit(produit: Produit)

    @Query("SELECT * FROM produit_table")
    fun readAllData():LiveData<List<Produit>>
}