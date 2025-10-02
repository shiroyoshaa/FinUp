package com.example.finup.Transactions.list

import com.example.finup.arch.LiveDataWrapper

interface TransactionListUiStateWrapper: LiveDataWrapper {

    interface Update: LiveDataWrapper.Update<ShowDateTitle>
    interface Read: LiveDataWrapper.Read<ShowDateTitle>

    interface Mutable: Update, Read

    class Base: Mutable, LiveDataWrapper.Abstract<ShowDateTitle>()
}