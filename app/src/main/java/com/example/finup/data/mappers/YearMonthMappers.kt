package com.example.finup.data.mappers

import com.example.finup.data.YearMonthCache
import com.example.finup.domain.YearMonth

fun YearMonthCache.yearMonthDataToDomain() = YearMonth(this.id,this.month,this.year)
fun List<YearMonthCache>.yearMonthListDataToDomain() = this.map { YearMonth(it.id,it.month,it.year) }

