package com.example.survivaltips.ddbb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Tip::class], version = 1)
abstract class TipRoomDatabase : RoomDatabase() {

    abstract fun tipDao(): TipDAO

    companion object {

        @Volatile
        private var INSTANCE: TipRoomDatabase? = null

        fun getDatabase(context: Context): TipRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance: TipRoomDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    TipRoomDatabase::class.java,
                    "tip_database").build()
                INSTANCE = instance
                return instance
            }

        }

    }

}
