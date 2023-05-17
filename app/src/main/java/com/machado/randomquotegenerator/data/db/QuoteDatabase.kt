package com.machado.randomquotegenerator.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.machado.randomquotegenerator.data.db.entity.QuoteEntity

@Database(
    entities = [QuoteEntity::class],
    version = 1
)
abstract class QuoteDatabase : RoomDatabase() {
    abstract val dao: QuoteDao
}