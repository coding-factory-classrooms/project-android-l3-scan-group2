package com.example.scanall.model

//permet de parser notre json
class ParseDataClass {

    data class Enter(
            val code:String,
            val product: Product
    )

    data class Product(
        val image_front_url: String,
        val brands : String ,
        val ingredients_text:String,
        val countries_lc:String
    )
}