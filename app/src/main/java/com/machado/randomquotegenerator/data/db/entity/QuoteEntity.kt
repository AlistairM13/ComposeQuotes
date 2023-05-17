package com.machado.randomquotegenerator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.machado.randomquotegenerator.domain.model.QuoteUI

@Entity(tableName = "quote")
data class QuoteEntity(
    val author: String,
    val content: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {
    fun toQuoteUI(): QuoteUI {
        return QuoteUI(
            author = author,
            content = content
        )
    }
}
