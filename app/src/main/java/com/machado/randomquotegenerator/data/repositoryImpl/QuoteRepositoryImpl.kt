package com.machado.randomquotegenerator.data.repositoryImpl

import com.machado.randomquotegenerator.data.db.QuoteDao
import com.machado.randomquotegenerator.data.remote.QuoteApi
import com.machado.randomquotegenerator.domain.model.QuoteUI
import com.machado.randomquotegenerator.domain.repository.QuoteRepository
import com.machado.randomquotegenerator.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class QuoteRepositoryImpl(
    private val api: QuoteApi,
    private val dao: QuoteDao
) : QuoteRepository {
    override fun getQuotes(): Flow<Resource<List<QuoteUI>>> = flow {
        emit(Resource.Loading())

        val savedQuotes = dao.getSavedQuotes().map { it.toQuoteUI() }

        try {
            val remoteRandomQuotes = api.getRandomQuote()
            if (remoteRandomQuotes.isSuccessful) {
                remoteRandomQuotes.body()?.let { response ->
                    emit(Resource.Success(data = response.map { it.toQuoteUI() }))
                } ?: emit(
                    Resource.Error(
                        "Error in fetching quote, viewing saved quotes now",
                        data = savedQuotes
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Something went wrong",
                    data = savedQuotes
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, please check your internet connection",
                    data = savedQuotes
                )
            )
        }
    }

    override fun saveQuote(quoteUI: QuoteUI) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertQuote(quote = quoteUI.toQuoteEntity())
        }
    }

    override fun deleteQuote(quoteUI: QuoteUI) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteQuote(quoteContent = quoteUI.content)
        }
    }
}