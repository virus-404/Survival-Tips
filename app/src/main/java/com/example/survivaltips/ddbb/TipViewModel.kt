package com.example.survivaltips.ddbb


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch



class TipViewModel (application: Application): AndroidViewModel (application) {

    private val repository : TipRepository = TipRepository(application)
    private val allTips: LiveData<List<Tip>>

     init {
         allTips = repository.getAllTips()
    }

    fun insert(tip: Tip) = viewModelScope.launch {
        repository.insert(tip)
    }

    fun getAllTips(): LiveData<List<Tip>> {
        return allTips
    }
}