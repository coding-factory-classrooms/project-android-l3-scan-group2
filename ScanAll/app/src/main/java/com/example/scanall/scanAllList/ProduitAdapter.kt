package com.example.scanall.scanAllList

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scanall.data.DataProduit
import com.example.scanall.databinding.ItemProduitBinding
import java.net.URL


//adapter pour la gestion des données
class ProduitAdapter(private var dataProduit : List<DataProduit>,var clickListner: onClickItem)
    : RecyclerView.Adapter<ProduitAdapter.ViewHolder>() {

    var urlWelcome: URL? = null
    //notre class view holder avec ununique attribut binding
    class ViewHolder(val binding: ItemProduitBinding) : RecyclerView.ViewHolder(binding.root){
        //rajout pour le click
        fun init(item: DataProduit, action: onClickItem) {
            binding.titreProduitTextView.text = item.produitName
            binding.produitCodeBareTextView.text = item.produitCodeBare
            binding.descriptionProduitTextView.text = item.produitIngredients
            //charger l'image
            //var urllink = item.produitDescription
            //val url = URL("$urllink")
            //val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            //binding.imageView.setImageBitmap(image)
            //binding.imageView.setImageURI("ff")
            Glide.with(this.binding.imageView).load("${item.produitDescription}").into(binding.imageView)
            itemView.setOnClickListener{
                action.onClick(item, adapterPosition)
            }
        }
    }



    //utilisation de l'interface avec le binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProduitBinding.inflate(inflater,parent,false )
        return  ViewHolder(binding)
    }


    //associer des données à notre interface
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //récupération de la position des produits
        val produit = dataProduit[position]
        //with
        /*
        with(holder.binding){

            titreProduitTextView.text = produit.produitName
            produitCodeBareTextView.text = produit.produitCodeBare
            descriptionProduitTextView.text = produit.produitDescription
        }*/
        holder.init(dataProduit.get(position), clickListner)

    }

    override fun getItemCount(): Int = dataProduit.size

    //hydratation des donnees
    fun updateDataSet(dataProduit: List<DataProduit>) {
        this.dataProduit = dataProduit
        notifyDataSetChanged()
    }

}

interface onClickItem{
    fun onClick(dataProduit: DataProduit, position: Int)
}