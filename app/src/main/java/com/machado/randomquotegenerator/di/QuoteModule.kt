package com.machado.randomquotegenerator.di

import android.app.Application
import androidx.room.Room
import com.machado.randomquotegenerator.data.db.QuoteDatabase
import com.machado.randomquotegenerator.data.remote.QuoteApi
import com.machado.randomquotegenerator.data.repositoryImpl.QuoteRepositoryImpl
import com.machado.randomquotegenerator.domain.repository.QuoteRepository
import com.machado.randomquotegenerator.domain.use_case.DeleteQuoteUseCase
import com.machado.randomquotegenerator.domain.use_case.GetQuoteUseCase
import com.machado.randomquotegenerator.domain.use_case.SaveQuoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuoteModule {

    @Provides
    @Singleton
    fun provideGetQuoteUseCase(repository: QuoteRepository): GetQuoteUseCase {
        return GetQuoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveQuoteUseCase(repository: QuoteRepository): SaveQuoteUseCase {
        return SaveQuoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteQuoteUseCase(repository: QuoteRepository): DeleteQuoteUseCase {
        return DeleteQuoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideQuoteRepository(api: QuoteApi, db: QuoteDatabase): QuoteRepository {
        return QuoteRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideQuoteApi(): QuoteApi {
        return Retrofit.Builder()
            .baseUrl(QuoteApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuoteDatabase(app: Application): QuoteDatabase {
        return Room.databaseBuilder(
            app,
            QuoteDatabase::class.java,
            "quote_db"
        ).build()
    }


}