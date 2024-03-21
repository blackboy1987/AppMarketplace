package com.bootx.app.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bootx.app.repository.entity.DownloadManagerEntity

@Dao
interface DownloadManagerDao {

    @Query("select * from downloadManager order by createDate desc")
    fun getAll(): List<DownloadManagerEntity>

    @Insert
    fun insert(downloadManager: DownloadManagerEntity)

    @Update
    fun update(downloadManager: DownloadManagerEntity)
    @Query("select * from downloadManager where id = :id")
    fun getById(id: Int): List<DownloadManagerEntity>

    @Query("delete from downloadManager where 1=1")
    fun removeAll()
}