package com.example.scanall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scanall.model.Produit

//source de données

class ScanAllListViewModel : ViewModel() {
    //definir nos attributs
    private val produitsLiveData = MutableLiveData<List<Produit>>()
    //un getter de type Prduit
    fun getProduitLiveData(): LiveData<List<Produit>> = produitsLiveData

    //func pour notifier la list de nos produits



}