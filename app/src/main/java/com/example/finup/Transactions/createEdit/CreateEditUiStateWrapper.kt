package com.example.finup.Transactions.createEdit

import com.example.finup.core.LiveDataWrapper

interface CreateEditUiStateWrapper: LiveDataWrapper {

    interface Update: LiveDataWrapper.Update<CreateEditUiState>
    interface Read: LiveDataWrapper.Read<CreateEditUiState>

    interface Mutable: Update, Read

    class Base: Mutable, LiveDataWrapper.Abstract<CreateEditUiState>()

}