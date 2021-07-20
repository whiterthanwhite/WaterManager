package com.bst.watermanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bst.watermanager.adapter.ContainersAdapter
import com.bst.watermanager.databinding.WaterMainBinding
import com.bst.watermanager.db.AppDatabase
import com.bst.watermanager.model.Container
import com.bst.watermanager.repository.ContainerRepo
import com.bst.watermanager.util.DateUtils
import com.bst.watermanager.viewmodel.ContainersViewModel

class WaterMain : AppCompatActivity(), ContainersAdapter.ContainerAdapterListener,
    AddContainerFragment.AddContainerListener
{
    private val TAG = "WATER_MAIN"
    private var contViewModel: ContainersViewModel? = null

    private lateinit var waterMainBdg : WaterMainBinding
    private lateinit var contAdap : ContainersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waterMainBdg = WaterMainBinding.inflate(layoutInflater)
        setContentView(waterMainBdg.root)
        setupViewModels()
        updateControls()
        setFAB()
    }

    private fun setupViewModels() {
        val db = AppDatabase.getInstance(this)
        val contRepo = ContainerRepo(db.containerDao())
        contViewModel = ContainersViewModel(contRepo)
    }

    private fun updateControls() {
        waterMainBdg.containerList.setHasFixedSize(true)

        val linearLayMgr = LinearLayoutManager(this)
        waterMainBdg.containerList.layoutManager = linearLayMgr

        val dividerItemDecoration = DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL
        )
        waterMainBdg.containerList.addItemDecoration(dividerItemDecoration)

        contAdap = ContainersAdapter(null, this, this)
        contViewModel?.getContainers()?.observe(this, {
            contAdap.setContainerList(it)
            waterMainBdg.containerList.adapter = contAdap
        })

        contViewModel?.getCurrVolStat()?.observe(this, {
            waterMainBdg.volumeAmount.text = it?.volume.toString()
        })
    }

    private fun setFAB() {
        val addContFAB = waterMainBdg.addContainer
        val addContFrag = AddContainerFragment()
        addContFAB.setOnClickListener {
            addContFrag.show(supportFragmentManager, null)
        }
    }

    override fun addVolume(cont: Container) {
        // TODO("Not yet implemented")
    }

    override fun removeVolume(cont: Container) {
        // TODO("Not yet implemented")
    }

    override fun deleteContainer(cont: Container) {
        contViewModel?.deleteContainer(cont)
    }

    override fun onDialogPositiveClick(cont: Container) {
        contViewModel?.insertContainer(cont)
    }

    override fun onDialogNegativeClick() {
        // TODO("Not yet implemented")
    }
}