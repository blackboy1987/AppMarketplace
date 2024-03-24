package com.bootx.app.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "downloadManager"
)
data class DownloadManagerEntity(
    @PrimaryKey
    var id: Int=0,
    var name: String="",
    var logo: String="",
    var packageName: String,
    var path: String,
    var createDate: Long = Date().time,
)
