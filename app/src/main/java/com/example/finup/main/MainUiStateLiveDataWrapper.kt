package com.example.finup.main

import com.example.finup.core.LiveDataWrapper

interface MainUiStateLiveDataWrapper : LiveDataWrapper {

    interface Update : LiveDataWrapper.Update<MainUiState>
    interface Read : LiveDataWrapper.Read<MainUiState>

    interface Mutable : Update, Read

    class Base : Mutable, LiveDataWrapper.Abstract<MainUiState>()
}