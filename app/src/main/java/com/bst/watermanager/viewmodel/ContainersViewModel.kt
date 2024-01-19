package com.bst.watermanager.viewmodel

import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bst.watermanager.model.Container
import com.bst.watermanager.model.VolumeStatistic
import com.bst.watermanager.repository.ContainerRepo
import com.bst.watermanager.util.DateUtils
import kotlinx.coroutines.*
import java.util.*

class ContainersViewModel(
    private val contRepo: ContainerRepo?
) : ViewModel()
{
    private var contList: LiveData<List<Container>>? = null
    private var cont: LiveData<Container>? = null
    private var volStat: LiveData<VolumeStatistic>? = null
    var _volStat: MutableLiveData<VolumeStatistic> = MutableLiveData()

    fun getContainers() : LiveData<List<Container>>? {
        val repo = contRepo ?: return contList
        return repo.getContainers()
    }

    fun getContainer(contId : Int) : LiveData<Container>? {
        val repo = contRepo ?: return cont
        return repo.getContainer(contId)
    }

    private suspend fun insertContainerC(container: Container) =
        withContext(Dispatchers.IO)
    {
        contRepo?.let {
            it.insertContainer(container)
        }
    }

    fun insertContainer(container: Container) = GlobalScope.launch {
        insertContainerC(container)
    }

    private suspend fun deleteContainerC(container: Container) =
        withContext(Dispatchers.IO)
    {
        contRepo?.let {
            it.deleteContainer(container)
        }
    }

    fun deleteContainer(container: Container) = GlobalScope.launch {
        deleteContainerC(container)
    }

    fun getCurrVolStat() : LiveData<VolumeStatistic>? {
        var repo = contRepo ?: return null
        volStat = repo.getCurrVolStat()
        getCurrVolStatAsync()
        return volStat
    }

    private fun getCurrVolStatAsync() = GlobalScope.launch {
        contRepo?.let {
            repo ->
            val job = async { repo.getCurrVolStatAsync() }
            var tmpVolStat = job.await()
            if (tmpVolStat == null) {
                tmpVolStat = VolumeStatistic(DateUtils.getCurrentDate(), 0)
                insertVolStat(tmpVolStat)
                _volStat.postValue(tmpVolStat)
                volStat = _volStat
            }
        }
    }

    fun insertVolStat(volStat: VolumeStatistic) = GlobalScope.launch {
        contRepo?.let {
            it.updateCurrVolStat(volStat)
        }
    }

    fun getVolStat() : VolumeStatistic? {
        return volStat?.value
    }
}