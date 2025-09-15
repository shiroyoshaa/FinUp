package com.example.finup.core

import android.R.attr.order
import android.service.autofill.Validators.or
import androidx.lifecycle.LiveData
import androidx.test.runner.screenshot.Screenshot
import org.junit.Assert.assertEquals
import kotlin.jvm.Throws


interface FakeNavigation : Navigation.Mutable {


    fun check(expected: Screen)

    companion object {
        private const val NAVIGATION = "navigation#update"
    }

    class Base(private val order: Order) : Mutable {

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

