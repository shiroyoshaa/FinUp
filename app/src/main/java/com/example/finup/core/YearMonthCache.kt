package com.example.finup.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity("date_table",
    indices = [Index(value = ["month_value","year_value"], unique = true)]
)
data class YearMonthCache(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "month_value") val month: Int,
    @ColumnInfo(name = "year_value") val year: Int
)
