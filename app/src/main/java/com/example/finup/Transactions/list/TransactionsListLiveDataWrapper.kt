package com.example.finup.Transactions.list

interface TransactionsListLiveDataWrapper {

    interface Update{
        fun update(value: List<DisplayItem>)
    }

}