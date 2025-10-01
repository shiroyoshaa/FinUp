package com.example.finup.data

import com.example.finup.domain.Now
import com.example.finup.domain.Transaction
import com.example.finup.domain.TransactionRepository.EditAndCreate
import com.example.finup.domain.TransactionRepository.GetTransactions

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
        val transaction = Transaction(
            transactionCache.id,
            transactionCache.sum,
            transactionCache.name,
            transactionCache.type,
            transactionCache.day,
            transactionCache.dateId
        )
        return transaction
    }

    override suspend fun getTransactions(dateId: Long, type: String): List<Transaction> {
        val listTransactions = transactionDao.getTransactions(dateId, type)
        val newConvertedTransactions = listTransactions.map {
            Transaction(
                it.id,
                it.sum,
                it.name,
                it.type,
                it.day,
                it.dateId
            )
        }
        return newConvertedTransactions
    }

    override suspend fun deleteTransaction(id: Long) {
        transactionDao.delete(id)
    }
}