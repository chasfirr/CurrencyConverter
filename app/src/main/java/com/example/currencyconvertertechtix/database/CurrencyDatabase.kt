package com.example.currencyconvertertechtix.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconvertertechtix.Currency

@Database(entities = [Currency::class], version = 3)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDAO(): CurrenciesDAO

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getDB(context: Context): CurrencyDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CurrencyDatabase::class.java, "CurrencyDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}