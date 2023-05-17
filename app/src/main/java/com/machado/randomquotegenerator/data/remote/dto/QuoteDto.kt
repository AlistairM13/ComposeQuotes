package com.machado.randomquotegenerator.data.remote.dto

import com.machado.randomquotegenerator.data.db.entity.QuoteEntity
import com.machado.randomquotegenerator.domain.model.QuoteUI

data class QuoteDto(
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
) {
    fun toQuoteEntity(): QuoteEntity {
        return QuoteEntity(
            author = author,
            content = content
        )
    }

    fun toQuoteUI(): QuoteUI {
        return QuoteUI(
            author = author,
            content = content
        )
    }
}