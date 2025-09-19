package com.example.finup.core

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [DateItemCache::class, TransactionCache::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun dateItemDao(): DateItemDao
    abstract fun transactionDao(): TransactionDao

}