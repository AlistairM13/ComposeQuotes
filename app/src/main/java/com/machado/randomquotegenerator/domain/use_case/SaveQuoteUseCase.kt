package com.machado.randomquotegenerator.domain.use_case

import com.machado.randomquotegenerator.domain.model.QuoteUI
import com.machado.randomquotegenerator.domain.repository.QuoteRepository

class SaveQuoteUseCase(
    private val repository: QuoteRepository
) {
    operator fun invoke(quoteUI: QuoteUI) {
        repository.saveQuote(quoteUI)
    }
}