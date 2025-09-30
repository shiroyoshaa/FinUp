package com.example.finup.domain

import com.example.finup.domain.Now
import com.example.finup.data.TransactionCache
import com.example.finup.data.TransactionDao


interface TransactionRepository {


    interface CreateTransaction {
        suspend fun createTransaction(
            sum: Int,
            name: String,
            type: String,
            day: Int,
            dateId: Long
        ): Long
    }

    interface GetTransaction {
        suspend fun getOneTransaction(id: Long, type: String): Transaction
    }

    interface EditTransaction {
        suspend fun editTransaction(
            transactionId: Long,
            sum: Int,
            name: String,
            type: String,
            day: Int,
            dateId: Long
        )
    }

    interface DeleteTransaction {
        suspend fun deleteTransaction(id: Long)
    }

    interface GetTransactions {
        suspend fun getTransactions(dateId: Long, type: String): List<Transaction>
    }

    interface ReadList : GetTransactions

    interface EditAndCreate : GetTransaction, EditTransaction, DeleteTransaction, CreateTransaction

    class Base(
        private val transactionDao: TransactionDao,
        private val now: Now
    ) : EditAndCreate, ReadList {

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
}
