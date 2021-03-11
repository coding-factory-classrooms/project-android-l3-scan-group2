package com.example.scanall.scanAllList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanall.Produit
import com.example.scanall.databinding.ItemProduitBinding

//adapter pour la gestion des données
class ProduitAdapter(private var produits : List<Produit>)
    : RecyclerView.Adapter<ProduitAdapter.ViewHolder>() {

    //notre class view holder avec ununique attribut binding
    class ViewHolder(val binding: ItemProduitBinding) : RecyclerView.ViewHolder(binding.root)


    //utilisation de l'interface avec le binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProduitBinding.inflate(inflater,parent,false )
        return  ViewHolder(binding)
    }


    //associer des données à notre interface
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //récupération de la position des produits
        val produit = produits[position]
        //with
        with(holder.binding){
            titreProduitTextView.text = produit.title
            produitCodeBareTextView.text = produit.codebare.toString()
            descriptionProduitTextView.text = produit.description
        }
    }

    override fun getItemCount(): Int = produits.size

    //hydratation des donnees
    fun updateDataSet(produits: List<Produit>) {
        this.produits = produits
        notifyDataSetChanged()
    }

}