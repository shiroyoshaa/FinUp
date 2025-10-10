package com.example.finup.main

import com.example.finup.core.LiveDataWrapper


interface Navigation: LiveDataWrapper {

    interface Update: LiveDataWrapper.Update<Screen>

    interface Read: LiveDataWrapper.Read<Screen>

    interface Mutable: Update, Read

    class Base: Mutable, LiveDataWrapper.Abstract<Screen>()

}