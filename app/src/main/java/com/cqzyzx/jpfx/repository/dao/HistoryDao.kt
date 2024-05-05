package com.cqzyzx.jpfx.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cqzyzx.jpfx.repository.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Query("select * from history order by updateDate desc")
    fun getAll(): List<HistoryEntity>

    @Insert
    fun insert(historyEntity: HistoryEntity)

    @Update
    fun update(historyEntity: HistoryEntity)
    @Query("select * from history where id = :id")
    fun getById(id: Int): List<HistoryEntity>

    @Query("delete from history where 1=1")
    fun delete()
}