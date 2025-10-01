package com.example.finup.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface YearMonthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(yearMonthCache: YearMonthCache)

    @Query("SELECT * FROM DATE_TABLE WHERE id = :id")
    suspend fun getDateItem(id: Long): YearMonthCache


    @Query("SELECT * FROM DATE_TABLE ORDER BY year_value, month_value")
    suspend fun getAllPeriods(): List<YearMonthCache>

    @Query("DELETE FROM DATE_TABLE WHERE id = :dateId")
    suspend fun delete(dateId: Long)
}