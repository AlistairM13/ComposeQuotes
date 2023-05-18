package com.machado.randomquotegenerator.domain.use_case

import com.machado.randomquotegenerator.domain.model.QuoteUI
import com.machado.randomquotegenerator.domain.repository.QuoteRepository

class DeleteQuoteUseCase(
    private val repository: QuoteRepository
) {
    operator fun invoke(quoteUI: QuoteUI) {
        repository.deleteQuote(quoteUI)
    }
}