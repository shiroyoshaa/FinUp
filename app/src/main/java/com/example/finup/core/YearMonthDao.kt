package com.example.finup.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface YearMonthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dateTitleCache: YearMonthCache)

    @Query("SELECT * FROM DATE_TABLE WHERE month_value = :month AND year_value = :year")
    suspend fun getDateItem(month: Int, year: Int): YearMonthCache?
}