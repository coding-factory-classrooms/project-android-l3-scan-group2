package com.example.scanall.scanAllList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scanall.Produit
//source de données
private val products = listOf<Produit>(
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link"),
    Produit("riz", "riz poulet piment", "12/12/2006", 12342567, "link")
)
class ScanAllListViewModel : ViewModel() {
    //definir nos attributs
    private val produitsLiveData = MutableLiveData<List<Produit>>()
    //un getter de type Prduit
    fun getProduitLiveData(): LiveData<List<Produit>> = produitsLiveData

    //func pour notifier la list de nos produits
    fun loadProduit(){
        produitsLiveData.value = products
    }


}