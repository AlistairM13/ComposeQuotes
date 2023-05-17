package com.machado.randomquotegenerator.data.db.entity

import androidx.room.Entity

@Entity(tableName = "quote")
data class QuoteEntity(
    val author: String,
    val content: String,
)
