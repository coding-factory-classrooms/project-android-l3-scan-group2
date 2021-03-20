package com.example.scanall.data

import androidx.lifecycle.LiveData
import androidx.room.*

//data access methods is here
@Dao
interface ProduitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    //fun to add produit
    suspend fun addProduit(dataProduit: DataProduit)

    //delet produit
    @Delete
    suspend fun deleteProduit(dataProduit: DataProduit)

    //fun to read all data with a query table produits dans DataProduit
    @Query("SELECT * FROM produits ORDER BY id ASC")
    fun readAllData(): LiveData<List<DataProduit>>
}