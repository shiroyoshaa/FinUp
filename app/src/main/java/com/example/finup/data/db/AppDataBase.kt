package com.example.finup.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finup.data.db.entities.TransactionCache
import com.example.finup.data.db.dao.TransactionDao
import com.example.finup.data.db.entities.YearMonthCache
import com.example.finup.data.db.dao.YearMonthDao

@Database(entities = [YearMonthCache::class, TransactionCache::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun dateItemDao(): YearMonthDao
    abstract fun transactionDao(): TransactionDao

}