package com.example.finup.Transactions.core

interface Now {
    fun timeInMills(): Long
    class Base: Now {
        override fun timeInMills(): Long {
            return System.currentTimeMillis()
        }
    }
}