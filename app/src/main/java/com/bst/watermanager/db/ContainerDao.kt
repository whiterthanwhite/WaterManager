package com.bst.watermanager.db

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.bst.watermanager.model.Container
import com.bst.watermanager.model.VolumeStatistic
import java.util.*

@Dao
interface ContainerDao {
    @Query("SELECT * FROM Container ORDER BY uid")
    fun getAll(): LiveData<List<Container>>

    @Query("SELECT * FROM CONTAINER WHERE uid = :contId")
    fun getContainer(contId: Int) : Container

    @Query("SELECT * FROM VolumeStatistic ORDER BY date")
    fun getVolumeStat() : LiveData<List<VolumeStatistic>>

    @Query("SELECT * FROM VolumeStatistic WHERE date = :date")
    fun getDailyStat(date: Date) : LiveData<VolumeStatistic>?

    @Update(onConflict = REPLACE)
    fun updateDailyStat(volStat: VolumeStatistic)

    @Insert(onConflict = REPLACE)
    fun insertContainer(container: Container) : Long

    @Insert(onConflict = REPLACE)
    fun insertDailyStatistic(volStat: VolumeStatistic)

    @Delete
    fun deleteContainer(container: Container)

    @Delete
    fun deleteDailyStat(volStat: VolumeStatistic)
}