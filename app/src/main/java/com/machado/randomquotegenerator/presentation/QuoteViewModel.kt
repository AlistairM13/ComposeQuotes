package com.machado.randomquotegenerator.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machado.randomquotegenerator.domain.model.QuoteUI
import com.machado.randomquotegenerator.domain.use_case.DeleteQuoteUseCase
import com.machado.randomquotegenerator.domain.use_case.GetQuoteUseCase
import com.machado.randomquotegenerator.domain.use_case.SaveQuoteUseCase
import com.machado.randomquotegenerator.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val saveQuoteUseCase: SaveQuoteUseCase,
    private val deleteQuoteUseCase: DeleteQuoteUseCase
) : ViewModel() {

    // For UI state
    private val _state = mutableStateOf(QuoteState())
    val state: State<QuoteState> = _state

    // For one time events like showing snackBar
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow: SharedFlow<UIEvent> = _eventFlow.asSharedFlow()

    init {
        getQuotes()
    }

    fun getQuotes() = viewModelScope.launch {
        getQuoteUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        quotes = result.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        quotes = result.data ?: emptyList(),
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UIEvent.ShowSnackBar(result.message ?: "Error fetching quotes")
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        quotes = result.data ?: emptyList(),
                        isLoading = true
                    )
                }
            }
        }.launchIn(this)
    }

    fun saveQuote(quoteUI: QuoteUI) = viewModelScope.launch {
        saveQuoteUseCase(quoteUI)
        _eventFlow.emit(
            UIEvent.ShowSnackBar("Quote Saved")
        )
    }

    fun deleteQuote(quoteUI: QuoteUI) = viewModelScope.launch {
        deleteQuoteUseCase(quoteUI)
        _eventFlow.emit(
            UIEvent.ShowSnackBar("Quote Deleted")
        )
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}