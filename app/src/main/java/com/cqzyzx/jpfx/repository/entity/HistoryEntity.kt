package com.cqzyzx.jpfx.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "history"
)
data class HistoryEntity(
    @PrimaryKey
    var id: Int=0,
    var name: String="",
    var logo: String="",
    var packageName: String,
    var createDate: Long = Date().time,
    var updateDate: Long = Date().time
)