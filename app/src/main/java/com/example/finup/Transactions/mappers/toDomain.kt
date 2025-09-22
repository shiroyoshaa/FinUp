package com.example.finup.Transactions.mappers

import com.example.finup.Transactions.core.YearMonth
import com.example.finup.core.YearMonthCache

fun YearMonthCache.toDomain() = YearMonth(this.id,this.month,this.year)

