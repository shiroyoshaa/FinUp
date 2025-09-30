package com.example.finup.data.mappers

import com.example.finup.data.YearMonthCache
import com.example.finup.domain.YearMonth

fun YearMonthCache.toDomain() = YearMonth(this.id,this.month,this.year)
fun List<YearMonthCache>.listToDomain() = this.map { YearMonth(it.id,it.month,it.year) }

