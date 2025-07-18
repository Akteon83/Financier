package com.atech.financier.data.local

import androidx.room.Room
import com.atech.financier.FinancierApplication

object RoomInstance {

    val database: FinancierDatabase by lazy {
        Room.databaseBuilder(
            FinancierApplication.context,
            FinancierDatabase::class.java,
            "financier.db"
        ).build()
    }

    /*var database: FinancierDatabase? = null
        private set

    fun initDatabase(context: Context) {
        if (database != null) return
        synchronized(this) {
            database = Room.databaseBuilder(
                context,
                FinancierDatabase::class.java,
                "financier.db"
            ).build()
        }
    }*/
}