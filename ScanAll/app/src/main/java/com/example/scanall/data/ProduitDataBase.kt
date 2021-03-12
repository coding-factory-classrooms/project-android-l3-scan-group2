package com.example.scanall.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scanall.model.Produit

//répresente notre databse en fonction du type de dataclass
@Database(entities = [Produit::class], version = 1,exportSchema = false)
abstract class ProduitDataBase:RoomDatabase() {

    abstract fun produitDao():ProduitDao

    //verification de instance ProduitDataBase
    companion object{
        @Volatile
        private var INSTANCE:ProduitDataBase?=null

        fun getDatabase(context: Context):ProduitDataBase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return  tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    ProduitDataBase::class.java,
                    "produit_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}