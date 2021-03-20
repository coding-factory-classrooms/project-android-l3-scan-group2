package com.example.scanall.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//this class is for ower entities table in database
@Entity(tableName = "produits")//table name
data class DataProduit (
    //premary key
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val produitName: String,
    val produitCodeBare: String,
    val produitDescription: String,
    val produitIngredients: String

)