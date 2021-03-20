package com.example.scanall.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProduitViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<DataProduit>>
    private val repository: ProduitRepository

    //method for execute viewmodel first
    init {
        val produitDao = ProduitDataBase.getDataBase(application).produitsDao()
        repository = ProduitRepository(produitDao)
        readAllData = repository.readAllData
    }

    //adding user in viewModel
    fun addProduit(dataProduit: DataProduit){
        //launch viewModel
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduits(dataProduit)
        }
    }

    //deliting produit
    fun deleteProduitVModel(dataProduit: DataProduit){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteProduitDao(dataProduit)
        }
    }
}