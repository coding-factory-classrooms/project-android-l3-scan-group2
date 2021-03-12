package com.example.scanall.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//represent notre table
@Entity(tableName = "produit_table")
//dataclass du produit
data class Produit(
    val id:Int,
    @PrimaryKey()
    val code:String,
    val image_front_url: String,
    val brands : String ,
    val ingredients_text:String,
    val countries_lc:String,
    val date_scan:String
)