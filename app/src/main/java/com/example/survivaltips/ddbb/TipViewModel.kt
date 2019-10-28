package com.example.survivaltips.ddbb

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TipViewModel (application: Application): AndroidViewModel (application) {

    private val repository : TipRepository
    val allTips: MediatorLiveData<List<Tip>> = MediatorLiveData ()

    init {
        val tipDAO = TipRoomDatabase.getDatabase(application).tipDao()
        repository = TipRepository(tipDAO)
        allTips.addSource(repository.allTips){
            this.allTips.value = it
        }
    }

    fun loadAll() {
        allTips.removeSource(allTips)
        allTips.addSource(repository.loadAll()){
            this.allTips.value = it
        }
    }

    fun insert(tip: Tip) = viewModelScope.launch {
        repository.insert(tip)
    }

    fun delete (tip: Tip)  = viewModelScope.launch {
        repository.delete(tip)
    }

    fun getAlphabetizedTips() {
        allTips.removeSource(allTips)
        allTips.addSource(repository.getAlphabetizedTips()){
            this.allTips.value = it
        }
    }

    fun getTipByName (name: String){
        allTips.removeSource(allTips)
        allTips.addSource(repository.getTipByName(name)){
            this.allTips.value = it
        }
    }

}