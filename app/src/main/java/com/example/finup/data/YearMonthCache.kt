package com.example.finup.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("date_table")

data class YearMonthCache(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "year_value") val year: Int,
    @ColumnInfo(name = "month_value") val month: Int,
)
