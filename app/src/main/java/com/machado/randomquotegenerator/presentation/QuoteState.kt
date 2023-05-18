package com.machado.randomquotegenerator.presentation

import com.machado.randomquotegenerator.domain.model.QuoteUI

data class QuoteState(
    val quotes: List<QuoteUI> = emptyList(),
    val isLoading: Boolean = false
)