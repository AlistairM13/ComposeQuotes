package com.machado.randomquotegenerator.domain.repository

import com.machado.randomquotegenerator.domain.model.QuoteUI
import com.machado.randomquotegenerator.util.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun getQuotes(): Flow<Resource<List<QuoteUI>>>

    fun saveQuote(quoteUI: QuoteUI)

    fun deleteQuote(quoteUI: QuoteUI)
}