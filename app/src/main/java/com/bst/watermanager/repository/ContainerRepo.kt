package com.bst.watermanager.repository

import androidx.lifecycle.LiveData
import com.bst.watermanager.db.ContainerDao
import com.bst.watermanager.model.Container
import com.bst.watermanager.model.VolumeStatistic
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ContainerRepo(
    private var contDao : ContainerDao
){
    fun getAll() : LiveData<List<Container>> {
        return contDao.getAll()
    }

    fun addContainer(container: Container) {
        GlobalScope.launch {
            val contId = contDao.insertContainer(container)
        }
    }

    fun getVolStat() : LiveData<List<VolumeStatistic>> {
        return contDao.getVolumeStat()
    }

    fun getDailyStat(date: Date) : LiveData<VolumeStatistic>? {
        return contDao.getDailyStat(date)
    }

    fun addDailyStat(volStat: VolumeStatistic) {
        GlobalScope.launch {
            val id = contDao.insertDailyStatistic(volStat)
        }
    }

    fun updateDailyStat(volStat: VolumeStatistic) {
        contDao.updateDailyStat(volStat)
    }
}