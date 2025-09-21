package com.example.finup.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("transaction_table")
data class TransactionCache(
    @PrimaryKey val id: Long,
    @ColumnInfo("sum_value") val sum: Int,
    @ColumnInfo("transaction_name") val name: String,
    @ColumnInfo("transaction_type") val type: String,
    @ColumnInfo("day_value") val day: Int,
    @ColumnInfo("date_id") val dateId: Long
)
