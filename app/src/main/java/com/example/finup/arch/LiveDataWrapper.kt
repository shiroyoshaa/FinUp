package com.example.finup.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    interface Update<T: Any>{
    fun update(value: T)
    }
    interface Read<T: Any>{
        fun liveData(): LiveData<T>
    }

    interface Mutable<T: Any>: Update<T>, Read<T>

    abstract class Abstract <T: Any>(protected val liveData: MutableLiveData<T> = SingleLiveEvent()): Mutable<T> {

        override fun update(value: T) {
            liveData.value = value
        }

        override fun liveData(): LiveData<T> = liveData
    }
}