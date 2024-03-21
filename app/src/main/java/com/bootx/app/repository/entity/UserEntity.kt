package com.bootx.app.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)
data class UserEntity(
    @PrimaryKey
    var id: Int=0,
    var avatar: String="",
    var username: String="",
    var point: Int = 0,
    var nextPoint: Int = 1000,
    var concernCount: Int = 0,
    var fanCount: Int = 0,
    var payCount: Int = 0,
)