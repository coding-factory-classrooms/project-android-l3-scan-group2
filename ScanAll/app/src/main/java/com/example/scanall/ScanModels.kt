package com.example.scanall

//data class produit, nb: on ne peut heriter d'une data class, et impossible de changer les données de la class
data class Produit(var title: String, var description: String, var date: String, var codebare: Int, var image: String)

//func création
fun creatMovie(): Produit{
    return Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link")
}