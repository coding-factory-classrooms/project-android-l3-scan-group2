package com.example.scanall.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.scanall.model.Produit
import com.example.scanall.data.ProduitDataBase
import com.example.scanall.repository.ProduitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Permet d'affecter des données a la view
class ProduitViewModel(application: Application):AndroidViewModel(application) {
    val readAllData:LiveData<List<Produit>>
    private  val repository: ProduitRepository

    init{
        val produitDao= ProduitDataBase.getDatabase(application).produitDao()
        repository= ProduitRepository(produitDao)
        readAllData=repository.readAllData
    }

    fun addProduit(produit: Produit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduit(produit)
        }
    }
}