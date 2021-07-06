package com.bst.watermanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bst.watermanager.model.Container
import com.bst.watermanager.model.VolumeStatistic
import com.bst.watermanager.repository.ContainerRepo
import java.util.*

class ContainersViewModel : ViewModel() {
    var contRepo: ContainerRepo? = null
    var liveCont: LiveData<List<Container>>? = null
    var liveVolStat: LiveData<List<VolumeStatistic>>? = null
    var liveDailyStat: LiveData<VolumeStatistic>? = null

    fun getContainers() : LiveData<List<Container>>? {
        val repo = contRepo ?: return null
        if (liveCont == null) {
            val liveData = repo.getAll()
            liveCont = Transformations.map(liveData) {
                containerList ->
                containerList.map {
                    container ->
                    Container(name = container.name, volume = container.volume)
                }
            }
        }
        return liveCont
    }

    fun addContainer(container: Container) {
        var repo = contRepo ?: return
        container?.let {
            repo.addContainer(it)
        }
    }

    fun createDailyStat(date: Date) {
        val repo = contRepo ?: return
        if (liveDailyStat == null) {
            val volStat = VolumeStatistic(date, 0)
            repo.addDailyStat(volStat)
        }
    }

    fun getDailyStat(date: Date) : LiveData<VolumeStatistic>? {
        val repo = contRepo ?: return null
        if (liveDailyStat == null) {
            val liveData = repo.getDailyStat(date)
            liveData?.let {
                liveDailyStat = Transformations.distinctUntilChanged(it)
            }
        }
        return liveDailyStat
    }

    fun updateDailyStat(volStat: VolumeStatistic) {
        val repo = contRepo ?: return
        repo.updateDailyStat(volStat)
    }
}