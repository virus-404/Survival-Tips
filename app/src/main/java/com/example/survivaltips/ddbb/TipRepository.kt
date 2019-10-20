package com.example.survivaltips.ddbb

import android.app.Application
import androidx.lifecycle.LiveData

//Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class TipRepository (application: Application){

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    private var tipDAO: TipDAO
    private var  allTips: LiveData<List<Tip>>

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.

    init {
        val db : TipRoomDatabase = TipRoomDatabase.getDatabase(application)
        tipDAO = db.tipDao()
        allTips = tipDAO.getAll()
    }

    suspend fun insert(tip: Tip){
        tipDAO.insert(tip)
    }

    fun getAllTips(): LiveData<List<Tip>> {
        return allTips
    }
}