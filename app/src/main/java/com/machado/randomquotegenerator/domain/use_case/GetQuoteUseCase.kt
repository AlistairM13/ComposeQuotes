package com.machado.randomquotegenerator.domain.use_case

import com.machado.randomquotegenerator.domain.model.QuoteUI
import com.machado.randomquotegenerator.domain.repository.QuoteRepository
import com.machado.randomquotegenerator.util.Resource
import kotlinx.coroutines.flow.Flow

class GetQuoteUseCase(
    private val repository: QuoteRepository
) {
    operator fun invoke(): Flow<Resource<List<QuoteUI>>> {
        return repository.getQuotes()
    }
}