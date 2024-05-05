package com.cqzyzx.jpfx.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cqzyzx.jpfx.repository.entity.UserEntity

@Dao
interface UserDao {

    @Query("select * from user")
    fun getAll(): List<UserEntity>

    @Insert
    fun insert(userEntity: UserEntity)

    @Update
    fun update(userEntity: UserEntity)
    @Query("select * from user where id = :id")
    fun getById(id: Int): List<UserEntity>

    @Query("delete from user where 1=1")
    fun delete()
}