package com.example.finup.data

import com.example.finup.data.repositories.Now

interface FakeNow : Now {
    class Base(private var time: Long) : FakeNow {
        override fun timeInMills(): Long {
            return time++
        }
    }
}
