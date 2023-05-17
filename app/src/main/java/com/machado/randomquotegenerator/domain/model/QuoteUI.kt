package com.machado.randomquotegenerator.domain.model

import com.machado.randomquotegenerator.data.db.entity.QuoteEntity

data class QuoteUI(
    val author: String,
    val content: String,
) {
    fun toQuoteEntity(): QuoteEntity {
        return QuoteEntity(
            author = author,
            content = content
        )
    }
}
