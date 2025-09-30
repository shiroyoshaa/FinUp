package com.example.finup.domain

interface Now {
    fun timeInMills(): Long
    class Base: Now {
        override fun timeInMills(): Long {
            return System.currentTimeMillis()
        }
    }
}