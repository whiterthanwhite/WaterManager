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

    @Query("SELECT * FROM Container WHERE uid = :contId")
    fun getContainer(contId: Int) : LiveData<Container>

    @Insert(onConflict = REPLACE)
    fun insertContainer(container: Container) : Long

    @Delete
    fun deleteContainer(container: Container)

    @Query("SELECT * FROM VolumeStatistic " +
                "WHERE date = :currDate " +
                "ORDER BY date DESC LIMIT 1")
    fun getCurrVolStat(currDate: Date) : LiveData<VolumeStatistic>

    @Insert(onConflict = REPLACE)
    fun insertCurrVolStat(volStat: VolumeStatistic)

    @Delete
    fun deleteCurrVolStat(volStat: VolumeStatistic)
}