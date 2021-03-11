package com.example.scanall

class ParseDataClass {

    data class Enter(
            val code:String,
            val product: Product
    )

    data class Product(
        val image_front_url: String,
        val brands : String ,
        //val ingredients_text_with_allergens:String,
        //val ingredients_text_debug:String,
        val ingredients_text:String
        //val ingredients_text_en_imported:String
    )
}