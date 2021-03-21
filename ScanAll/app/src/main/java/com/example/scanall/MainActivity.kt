package com.example.scanall

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.scanall.model.Produit
import com.example.scanall.viewmodel.ProduitViewModel
import com.example.scanall.databinding.ActivityMainBinding
import com.example.scanall.model.ParseDataClass
import com.example.scanall.scanAllList.ProduitActivity
import com.google.gson.Gson
import okhttp3.*
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val CAMERA_REQUEST_CODE=101

//Main permet de scanner et de naviguer vers la liste
class MainActivity : AppCompatActivity() {

    private lateinit var mProduitViewModelView: ProduitViewModel
    private lateinit var binding: ActivityMainBinding          //on déclare notre binding
    private lateinit var codeScanner:CodeScanner
    private lateinit var code: String //on déclare notre variable codeScanner
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //on vas chercher notre layout
        binding= ActivityMainBinding.inflate(layoutInflater)
        //maitenant on peut utiliser le binding avec le layout Activity_Main
        setContentView(binding.root)

        mProduitViewModelView = ViewModelProvider(this).get(ProduitViewModel::class.java)
        setupPermissions()
        codeScanner()
    }

    //permet d'affecter a notre texteview la valeur du scan
    private fun setText(text: TextView, value: String) {
        runOnUiThread { text.text = value }
    }

    //permet d'obtenir la date d'aujourd'hui
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    //permet d'insérer les données du scan dans la base de donnée
    private fun insertDataToDatabase(bodyIsParse: ParseDataClass.Enter){
        val produit= Produit(0, bodyIsParse.code, bodyIsParse.product.image_front_url, bodyIsParse.product.brands, bodyIsParse.product.ingredients_text, bodyIsParse.product.countries_lc, Calendar.getInstance().time.toString("dd/MM/yyyy"))
        mProduitViewModelView.addProduit(produit)
    }

    //méthoode pour gérer la camera(autoFocus,camera_back,flas désactivé...)
    private fun codeScanner(){
        codeScanner = CodeScanner(this, binding.scannerView)
        codeScanner.apply {
            camera=CodeScanner.CAMERA_BACK
            formats=CodeScanner.ALL_FORMATS
            autoFocusMode=AutoFocusMode.SAFE
            scanMode=ScanMode.SINGLE
            isAutoFocusEnabled=true
            isFlashEnabled=false

            //Le DecodeCallback nous renvoie la valeur du scan
            decodeCallback= DecodeCallback {
                runOnUiThread{
                    code=it.text
                    binding.tvTextView.text= "code: ${it.text}"
                    //
                    val url= "https://world.openfoodfacts.org/api/v0/product/$code.json"
                    val request=Request.Builder().url(url).build()
                    val client=OkHttpClient()
                    client.newCall(request).enqueue(object :Callback{
                        override fun onResponse(call: Call?, response: Response?) {
                            val body=response?.body()?.string()
                            //Log.e("onResponse","${body}")

                            var gson = Gson()
                            var bodyIsParse = gson?.fromJson(body, ParseDataClass.Enter::class.java)

                            //this@MainActivity.runOnUiThread(java.lang.Runnable {
                                //binding.tvTextView.text= "code: ${bodyIsParse.code}"
                                //binding.tvTextViewBrand.text= "titre: ${bodyIsParse.product.brands}"
                            //})
                            setText(binding.tvTextViewBrand,bodyIsParse.product.brands)
                            //Log.e("onResponseParseData","${bodyIsParse}")

                            insertDataToDatabase(bodyIsParse)
                        }
                        override fun onFailure(call: Call?, e: IOException?) {
                            Log.e("onFailure","Failed to execute request")
                        }
                    })
                }
            }

            //Le ErrorCallback nous renvoie l'error du scan
            errorCallback= ErrorCallback {
                runOnUiThread{
                    Log.e("Main","Camera initialization error: ${it.message}")
                }
            }

        }

        //quand on clic sur notre camera on peut re-scanner un code
        binding.scannerView.setOnClickListener{
            codeScanner.startPreview()
        }

        //permet d'affecter une valeur en dur
        binding.buttonSimulate.setOnClickListener {
            binding.tvTextView.text="code: 20143855"
            //
            val url= "https://world.openfoodfacts.org/api/v0/product/20143855.json"
            val request=Request.Builder().url(url).build()
            val client=OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call?, response: Response?) {
                    val body=response?.body()?.string()
                    //Log.e("onResponse","${body}")

                    var gson = Gson()
                    var bodyIsParse = gson?.fromJson(body, ParseDataClass.Enter::class.java)

                    //this@MainActivity.runOnUiThread(java.lang.Runnable {
                    //binding.tvTextView.text= "code: ${bodyIsParse.code}"
                    //binding.tvTextViewBrand.text= "titre: ${bodyIsParse.product.brands}"
                    //})
                    setText(binding.tvTextViewBrand,bodyIsParse.product.brands)
                    //Log.e("onResponseParseData","${bodyIsParse}")

                    insertDataToDatabase(bodyIsParse)
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    Log.e("onFailure","Failed to execute request")
                }
            })
        }

        binding.buttonList.setOnClickListener {
            startActivity(Intent(this, ProduitActivity::class.java))
        }
    }

    //si l'application se Resume on peut re-scan un code
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    //on relaese les ressource de la camera avant de mettre en pause notre appli
    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    //pour tester les permissions de la camera en réferant notre manifest
    private fun setupPermissions(){
        val permission:Int=ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA)

        if(permission!=PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    //demande une permission de la camera en réferant notre manifest
    private  fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.CAMERA),
        CAMERA_REQUEST_CODE)
    }


    //en cas de refus des permissions a la camera
    fun onRequestPermissionsResul(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CAMERA_REQUEST_CODE->{
                if(grantResults.isEmpty() || grantResults[0] !=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"You need the camera permission to be able to use this app!",Toast.LENGTH_SHORT).show()
                }else{
                    //successful
                }
            }
        }
    }

}
