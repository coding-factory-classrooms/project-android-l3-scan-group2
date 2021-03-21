package com.example.scanall.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.scanall.model.Produit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProduitViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Produit>>
    private val repository: ProduitRepository

    //method for execute viewmodel first
    init {
        val produitDao = ProduitDataBase.getDatabase(application).produitDao()
        repository = ProduitRepository(produitDao)
        readAllData = repository.readAllData
    }

    //adding user in viewModel
    fun addProduit(produit: Produit){
        //launch viewModel
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduits(produit)
        }
    }

    //deliting produit
    fun deleteProduitVModel(produit: Produit){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteProduitDao(produit)
        }
    }
}