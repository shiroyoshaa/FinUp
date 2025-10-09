package com.example.finup.data.repositories

interface Now {
    fun timeInMills(): Long
    class Base: Now {
        override fun timeInMills(): Long {
            return System.currentTimeMillis()
        }
    }
}