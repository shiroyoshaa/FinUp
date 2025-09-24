package com.example.finup.Transactions.core

import com.example.finup.core.TransactionCache
import com.example.finup.core.TransactionDao


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

    interface Edit : GetTransaction, EditTransaction, DeleteTransaction


    class Base(
        private val transactionDao: TransactionDao,
        private val now: Now
    ) :  CreateTransaction, Edit, ReadList {

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
