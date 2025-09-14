package com.example.finup.core
import org.junit.Assert.assertEquals



class Order {
    private val actual = ArrayList<String>()

    fun add(name: String) {
        actual.add(name)
    }

    fun check(expected: ArrayList<String>) {
        assertEquals(expected, actual)
    }
}