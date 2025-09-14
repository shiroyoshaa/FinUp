package com.example.finup.core

import androidx.lifecycle.ViewModel
import org.junit.Assert.assertEquals

interface FakeClear : ClearViewModel {

    fun check(expected: Class<out ViewModel>)

    companion object {
        const val CLEAR = "Fake clear#clear"
    }

    class Base(private val order: Order) : FakeClear {

        private lateinit var actual: Class<out ViewModel>

        override fun clear(viewModelClass: Class<out ViewModel>) {
            actual = viewModelClass
            order.add(CLEAR)
        }

        override fun check(expected: Class<out ViewModel>) {
            assertEquals(expected,actual)
        }
    }
}
