package com.example.finup.data.repositories

import com.example.finup.data.db.dao.TransactionDao
import com.example.finup.data.db.entities.TransactionCache
import com.example.finup.data.mappers.transactionDataToDomain
import com.example.finup.data.mappers.transactionListDataToDomain
import com.example.finup.domain.models.Transaction
import com.example.finup.domain.repositories.TransactionRepository.EditAndCreate
import com.example.finup.domain.repositories.TransactionRepository.GetTransactions

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val now: Now
) : EditAndCreate, GetTransactions {

    override suspend fun createTransaction(
        sum: Int,
        name: String,
        type: String,
        day: Int,
        dateId: Long
    ): Long {
        val newId = now.timeInMills()
        val newTransactionCache = TransactionCache(newId, sum, name, type, day, dateId)
        transactionDao.insert(newTransactionCache)
        return newId
    }

    override suspend fun editTransaction(
        transactionId: Long,
        sum: Int,
        name: String,
        type: String,
        day: Int,
        dateId: Long
    ) {
        val newCache = TransactionCache(transactionId, sum, name, type, day, dateId)
        transactionDao.insert(newCache)
    }

    override suspend fun getOneTransaction(id: Long, type: String): Transaction {
        val transactionCache = transactionDao.getOneTransaction(id, type)
        return transactionCache.transactionDataToDomain()
    }

    override suspend fun getTransactions(dateId: Long, type: String): List<Transaction> {
        val listTransactions = transactionDao.getTransactions(dateId, type)
        return listTransactions.transactionListDataToDomain()
    }

    override suspend fun deleteTransaction(id: Long) {
        transactionDao.delete(id)
    }
}