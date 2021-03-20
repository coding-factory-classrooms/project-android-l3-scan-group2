package com.example.scanall.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//abstract class who extend room
//database creation with entities class
@Database(entities = [DataProduit::class], version = 1, exportSchema = false)
abstract class ProduitDataBase: RoomDatabase() {
    //fun to return our product
    abstract fun produitsDao(): ProduitDao

    //make our data base singleton visible
    companion object{
        @Volatile
        private  var INSTANCE: ProduitDataBase? = null

        fun getDataBase(context : Context): ProduitDataBase{
            val tempInstance = INSTANCE
            if(tempInstance !=  null)
            {
                return tempInstance
            }
            //creation d'un nouveau instance and de notre database
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProduitDataBase::class.java,
                    "produitDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
