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
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.budiyev.android.codescanner.*
import com.example.scanall.data.DataProduit
import com.example.scanall.data.ProduitViewModel
import com.example.scanall.databinding.ActivityMainBinding
import com.example.scanall.scanAllList.ProduitActivity
import com.example.scanall.scanAllList.ProduitAdapter
import com.example.scanall.scanAllList.onClickItem
import com.google.gson.Gson
import okhttp3.*
import java.io.FileDescriptor
import java.io.IOException
import java.util.*


private const val CAMERA_REQUEST_CODE=101


class MainActivity : AppCompatActivity(){

    private lateinit var mProduitViewModelView: ProduitViewModel
    private lateinit var binding: ActivityMainBinding          //on déclare notre binding
    private lateinit var codeScanner:CodeScanner
    private lateinit var code: String //on déclare notre variable codeScanner
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)   //on vas chercher notre layout
        setContentView(binding.root) //maitenant on peut utiliser le binding avec le layout Activity_Main
        mProduitViewModelView = ViewModelProvider(this).get(ProduitViewModel::class.java)

        binding.buttonList.setOnClickListener {
            startActivity(Intent(this, ProduitActivity::class.java))
            //startActivity(Intent(this, MainActivityModelView::class.java))
        }
        setupPermissions()
        codeScanner()
    }

    private fun setText(text: TextView, value: String) {
        runOnUiThread { text.text = value }
    }

    private fun codeScanner(){                                 //méthoode pour gérer la camera(autoFocus,camera_back,flas désactivé...)
        codeScanner = CodeScanner(this, binding.scannerView)
        codeScanner.apply {
            camera=CodeScanner.CAMERA_BACK
            formats=CodeScanner.ALL_FORMATS
            autoFocusMode=AutoFocusMode.SAFE
            scanMode=ScanMode.SINGLE
            isAutoFocusEnabled=true
            isFlashEnabled=false

            decodeCallback= DecodeCallback {                  //Le DecodeCallback nous renvoie la valeur du scan
                runOnUiThread{
                    code = it.text
                    binding.tvTextView.text="code: $code"
                    val url= "https://world.openfoodfacts.org/api/v0/product/$code.json"
                    val request=Request.Builder().url(url).build()
                    val client=OkHttpClient()
                    client.newCall(request).enqueue(object :Callback{
                        override fun onResponse(call: Call?, response: Response?) {
                            val body=response?.body()?.string()
                            Log.e("onResponse","${body}")


                            var bodyIsParse = gson?.fromJson(body, ParseDataClass.Enter::class.java)
                            //binding.textViewTitre.text = "Titre : ${bodyIsParse.product.brands}"
                            setText(binding.textViewTitre, bodyIsParse.product.brands)
                            Log.e("onResponseParseData","${bodyIsParse}")
                            //insertion des datas
                            insertDataToDatabase(bodyIsParse.product.brands, bodyIsParse.product.image_front_url, bodyIsParse.code.toString(), bodyIsParse.product.ingredients_text)
                        }

                        //method to insert data
                        private fun insertDataToDatabase(produitName: String, produitDescripton: String, produitCodeBare: String, produitIngredients:String) {
                            //insertion database
                            val produit = DataProduit(0, produitName, produitCodeBare,produitDescripton, produitIngredients)
                            mProduitViewModelView.addProduit(produit)
                            //Toast.makeText(this@MainActivity, "succesfull", Toast.LENGTH_LONG).show()


                        }


                        override fun onFailure(call: Call?, e: IOException?) {
                            Log.e("onFailure","Failed to execute request")
                        }
                    })
                    //
                }
            }

            errorCallback= ErrorCallback {                   //Le ErrorCallback nous renvoie l'error du scan
                runOnUiThread{
                    Log.e("Main","Camera initialization error: ${it.message}")
                }
            }

        }
        binding.scannerView.setOnClickListener{//quand on clic sur notre camera on peut re-scanner un code
            codeScanner.startPreview()
        }

        //simulation...
        binding.buttonSimulate.setOnClickListener {//permet d'affecter une valeur en dur
            binding.tvTextView.text="code: 3263855094175"
            val url= "https://world.openfoodfacts.org/api/v0/product/3700009269114.json"
            val request=Request.Builder().url(url).build()
            val client=OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call?, response: Response?) {
                    val body=response?.body()?.string()
                    Log.e("onResponse","${body}")

                    var gson = Gson()
                    var bodyIsParse = gson?.fromJson(body, ParseDataClass.Enter::class.java)

                    Log.e("onResponseParseData","${bodyIsParse}")
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    Log.e("onFailure","Failed to execute request")
                }
            })
        }
    }

    override fun onResume() {                       //si l'application se Resume on peut re-scan un code
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {                       //on relaese les ressource de la camera avant de mettre en pause notre appli
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions(){                //pour tester les permissions de la camera en réferant notre manifest
        val permission:Int=ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA)

        if(permission!=PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private  fun makeRequest(){                 //demande une permission de la camera en réferant notre manifest
        ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.CAMERA),
        CAMERA_REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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
