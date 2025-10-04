package com.example.finup.main

import com.example.finup.arch.LiveDataWrapper

interface TypeLiveDataWrapper: LiveDataWrapper {
    interface Update: LiveDataWrapper.Update<String>
    interface Read: LiveDataWrapper.Read<String>
    interface Mutable: Update, Read
    class Base: LiveDataWrapper.Abstract<String>(), Mutable
}