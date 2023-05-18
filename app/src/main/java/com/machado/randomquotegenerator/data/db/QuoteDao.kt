package com.machado.randomquotegenerator.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.machado.randomquotegenerator.data.db.entity.QuoteEntity

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)

    @Query("DELETE FROM quote WHERE content=:quoteContent")
    suspend fun deleteQuote(quoteContent: String)

    @Query("SELECT * FROM quote")
    suspend fun getSavedQuotes(): List<QuoteEntity>
}