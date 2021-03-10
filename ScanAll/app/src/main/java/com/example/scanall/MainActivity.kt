package com.example.scanall

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.scanall.databinding.ActivityMainBinding

private const val CAMERA_REQUEST_CODE=101

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding           //on déclare notre binding
    private lateinit var codeScanner:CodeScanner                //on déclare notre variable codeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)   //on vas chercher notre layout
        setContentView(binding.root)                           //maitenant on peut utiliser le binding avec le layout Activity_Main

        setupPermissions()
        codeScanner()
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
                    binding.tvTextView.text="code: ${it.text}"
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

        //
        binding.buttonSimulate.setOnClickListener {//permet d'affecter une valeur en dur
            binding.tvTextView.text="code: 3263855094175"
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

    override  fun onRequestPermissionsResult(requestCode:Int,permissions:Array<out String>,grantResults:IntArray){ //en cas de refus des permission on affiche un toast
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