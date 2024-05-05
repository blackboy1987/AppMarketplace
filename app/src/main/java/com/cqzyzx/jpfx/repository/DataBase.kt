package com.cqzyzx.jpfx.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cqzyzx.jpfx.repository.dao.DownloadManagerDao
import com.cqzyzx.jpfx.repository.dao.HistoryDao
import com.cqzyzx.jpfx.repository.entity.DownloadManagerEntity
import com.cqzyzx.jpfx.repository.entity.HistoryEntity
import com.cqzyzx.jpfx.repository.entity.UserEntity

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
        private var name = "yysc"
        fun getDb(context: Context) = if (db == null) {
            Room.databaseBuilder(context, DataBase::class.java, name)
                .enableMultiInstanceInvalidation().fallbackToDestructiveMigration().build().apply {
                db = this
            }
        } else {
            db
        }
    }
    abstract fun getHistoryDao(): HistoryDao

    abstract fun getDownloadManagerDao(): DownloadManagerDao

}