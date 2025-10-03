package com.example.finup.domain

interface YearMonthStateRepository {
    suspend fun getActiveYearMonthId(): Long
    suspend fun setActiveYearMonthId(id: Long)
}