package com.example.survivaltips.ddbb

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData

//Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class TipRepository (private val tipDAO: TipDAO){

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTips: LiveData<List<Tip>> = tipDAO.getAll()

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.

    suspend fun insert (tip: Tip){
        tipDAO.insert(tip)
    }

    fun getAlphabetizedTips (): LiveData<List<Tip>> {
        Log.i("Alphabet", tipDAO.getAlphabetizedTips().toString())
        return tipDAO.getAlphabetizedTips()
    }

    suspend fun delete (tip: Tip) {
        tipDAO.delete(tip)
    }
}