package com.example.finup.core

import androidx.lifecycle.LiveData
import com.example.finup.main.Navigation
import com.example.finup.main.Screen
import org.junit.Assert.assertEquals


interface FakeNavigation : Navigation.Mutable {


    fun check(expected: Screen)

    companion object {
        private const val NAVIGATION = "navigation#update"
    }

    class Base(private val order: Order) : FakeNavigation {

        private lateinit var actual: Screen

        override fun update(value: Screen) {
            actual = value
            order.add(NAVIGATION)
        }

        override fun check(expected: Screen) {
            assertEquals(expected, actual)
        }

        override fun liveData(): LiveData<Screen> {
            throw IllegalStateException("not implemented")
        }
    }

}

