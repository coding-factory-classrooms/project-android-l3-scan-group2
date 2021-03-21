package com.example.scanall.scanAllList

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scanall.model.Produit
import com.example.scanall.databinding.ItemProduitBinding
import java.net.URL


//adapter pour la gestion des données
class ProduitAdapter(private var produits : List<Produit>, private val mListener: OnItemClickListener?)
    : RecyclerView.Adapter<ProduitAdapter.ViewHolder>() {

    var urlWelcome: URL? = null
    //notre class view holder avec ununique attribut binding
    class ViewHolder(val binding: ItemProduitBinding) : RecyclerView.ViewHolder(binding.root)
//startActivity(Intent(this, ProduitActivity::class.java))

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
        holder.itemView.setOnClickListener{
            mListener?.onItemClick(produit,position)
        }
        with(holder.binding){
            Glide.with(this.imageView).load("${produit.image_front_url}").into(imageView)
            produitCodeBareTextView.text = produit.countries_lc
            descriptionProduitTextView.text = produit.brands
        }
    }

    override fun getItemCount(): Int = produits.size

    //hydratation des donnees
    fun updateDataSet(produits: List<Produit>) {
        this.produits = produits
        notifyDataSetChanged()
    }

}

//interface pour gérer le click
interface OnItemClickListener {
    fun onItemClick(produits: Produit, position: Int)
}