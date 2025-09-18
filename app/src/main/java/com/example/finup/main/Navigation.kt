package com.example.finup.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


interface Navigation {
    interface Update {
        fun update(value: Screen)
    }

    interface Read {
        fun liveData(): LiveData<Screen>
    }

    interface Mutable : Update, Read
    class Base(private val liveData: MutableLiveData<Screen> = MutableLiveData()) : Mutable {
        override fun liveData(): LiveData<Screen> {
            return liveData
        }

        override fun update(value: Screen) {
            liveData.value = value
        }
    }
}