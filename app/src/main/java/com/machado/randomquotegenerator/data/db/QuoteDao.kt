package com.machado.randomquotegenerator.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.machado.randomquotegenerator.data.db.entity.QuoteEntity

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)

    @Delete
    suspend fun deleteQuote(quote:QuoteEntity)

    @Query("SELECT * FROM quote")
    fun getSavedQuotes(): List<QuoteEntity>
}