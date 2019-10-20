package com.example.survivaltips.ddbb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TipDAO {
    @Query("SELECT * FROM tip_table")
    fun getAll(): LiveData<List<Tip>>

    @Query("SELECT * FROM tip_table WHERE title LIKE :title")
    fun findByName(title: String): Tip

    @Query("SELECT * from tip_table ORDER BY title ASC")
    fun getAlphabetizedTips(): LiveData<List<Tip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg tip: Tip)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tips: Tip)

    @Delete
    fun delete(tip: Tip)

    @Query("DELETE FROM tip_table")
    fun deleteAll()
}