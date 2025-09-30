package com.example.finup.Transactions.list

import com.example.finup.Transactions.model.DisplayItemUi

interface TransactionsListLiveDataWrapper {

    interface UpdateList{
        fun update(value: List<DisplayItemUi>)
    }

}