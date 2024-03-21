package com.bootx.yysc.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bootx.app.repository.dao.DownloadManagerDao
import com.bootx.app.repository.dao.HistoryDao
import com.bootx.app.repository.dao.UserDao
import com.bootx.app.repository.entity.DownloadManagerEntity
import com.bootx.app.repository.entity.HistoryEntity
import com.bootx.app.repository.entity.UserEntity

@Database(
    version = 6,
    entities = [
        UserEntity::class,
        HistoryEntity::class,
        DownloadManagerEntity::class,
    ],
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {

    companion object {
        private var db: DataBase? = null
        private var name = "app"
        fun getDb(context: Context) = if (db == null) {
            Room.databaseBuilder(context, DataBase::class.java, name)
                .enableMultiInstanceInvalidation().fallbackToDestructiveMigration().build().apply {
                db = this
            }
        } else {
            db
        }
    }

    abstract fun getUserDao(): UserDao

    abstract fun getHistoryDao(): HistoryDao

    abstract fun getDownloadManagerDao(): DownloadManagerDao

}