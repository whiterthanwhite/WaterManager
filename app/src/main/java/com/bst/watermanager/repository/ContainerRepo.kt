package com.bst.watermanager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bst.watermanager.db.ContainerDao
import com.bst.watermanager.model.Container
import com.bst.watermanager.model.VolumeStatistic
import com.bst.watermanager.util.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ContainerRepo(
    private val contDao : ContainerDao?
) {
    fun getContainers() : LiveData<List<Container>> {
       val dao = contDao ?: return MutableLiveData()
       return dao.getAll()
    }

    fun getContainer(contId: Int) : LiveData<Container> {
        val dao = contDao ?: return MutableLiveData()
        return dao.getContainer(contId)
    }

    suspend fun insertContainer(container: Container) = withContext(Dispatchers.IO) {
        contDao?.let {
            it.insertContainer(container)
        }
    }

    suspend fun deleteContainer(container: Container) = withContext(Dispatchers.IO) {
        contDao?.let {
            it.deleteContainer(container)
        }
    }

    fun getCurrVolStat() : LiveData<VolumeStatistic> {
        val dao = contDao ?: return MutableLiveData()
        val currDate = DateUtils.getCurrentDate()
        return dao.getCurrVolStat(currDate)
    }

    suspend fun updateCurrVolStat(volStat: VolumeStatistic) =
        withContext(Dispatchers.IO)
    {
        contDao?.let {
            it.insertCurrVolStat(volStat)
        }
    }
}