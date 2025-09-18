package com.example.finup.core
import org.junit.Assert.assertEquals



class Order {
    private val actual = mutableListOf<String>()

    fun add(name: String) {
        actual.add(name)
    }

    fun check(expected: List<String>) {
        assertEquals(expected, actual)
    }
}