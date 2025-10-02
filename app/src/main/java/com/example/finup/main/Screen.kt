package com.example.finup.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {
    fun apply(fragmentManager: FragmentManager,containerId: Int)


    abstract class Replace(private val fragment: Fragment): Screen {
        override fun apply(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .replace(containerId,fragment)
                .commit()
        }
    }
}