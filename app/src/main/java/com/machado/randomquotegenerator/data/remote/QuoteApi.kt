package com.machado.randomquotegenerator.data.remote

import com.machado.randomquotegenerator.data.remote.dto.QuoteDto
import retrofit2.http.GET

interface QuoteApi {

    @GET("/quotes/random")
    suspend fun getRandomQuote(): List<QuoteDto>

    companion object{
        const val BASE_URL = "https://api.quotable.io/"
    }
}