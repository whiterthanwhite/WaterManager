package com.bst.watermanager.db

import android.content.Context
import androidx.room.*
import com.bst.watermanager.model.Container
import com.bst.watermanager.model.VolumeStatistic
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?) : Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toTimestamp(date: Date?) : Long? {
        return date?.time
    }
}

@Database(entities = arrayOf(Container::class, VolumeStatistic::class), version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun containerDao() : ContainerDao
    companion object {
        private var instance : AppDatabase? = null
        fun getInstance(context: Context) : AppDatabase {
            instance = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Water Manager").build()
            return instance as AppDatabase
        }
    }
}