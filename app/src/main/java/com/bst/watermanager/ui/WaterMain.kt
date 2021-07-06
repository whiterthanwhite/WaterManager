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
import com.bst.watermanager.repository.ContainerRepo
import com.bst.watermanager.util.DateUtils
import com.bst.watermanager.viewmodel.ContainersViewModel

class WaterMain : AppCompatActivity(), ContainersAdapter.ContainerAdapterListener,
    AddContainerFragment.AddContainerListener
{
    private val TAG = "WATER_MAIN"
    private val contViewModel: ContainersViewModel by viewModels()

    private lateinit var waterMainBdg : WaterMainBinding
    private lateinit var contAdap : ContainersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        waterMainBdg = WaterMainBinding.inflate(layoutInflater)
        setContentView(waterMainBdg.root)
        setupViewModels()
        updateControls()
        setupContainerListView()
    }

    private fun setupViewModels() {
        val db = AppDatabase.getInstance(this)
        val contDao = db.containerDao()
        contViewModel.contRepo = ContainerRepo(contDao)
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
        waterMainBdg.containerList.adapter = contAdap

        val addContFAB = waterMainBdg.addContainer
        val addContFrag = AddContainerFragment()
        addContFAB.setOnClickListener {
            addContFrag.show(supportFragmentManager, null)
        }
    }

    private fun setupContainerListView() {
        contViewModel.getContainers()?.observe(this, {
            it?.let {
                showContainers()
            }
        })
    }

    private fun showContainers() {
        val containers = contViewModel.getContainers()?.value
        containers?.let {
            contAdap.setContainerList(it)
        }
    }

    override fun addVolume(uid: Int?) {
        uid?.let {
            val cont = contViewModel.getContainer(uid)
            Log.i(TAG, cont.toString())
        }
    }

    override fun removeVolume(uid: Int?) {
        uid?.let {
            val cont = contViewModel.getContainer(uid)
            Log.i(TAG, cont.toString())
        }
    }

    override fun deleteContainer(uid: Int?) {
        // TODO("Not yet implemented")
    }

    override fun onDialogPositiveClick() {
        // TODO("Not yet implemented")
    }

    override fun onDialogNegativeClick() {
        // TODO("Not yet implemented")
    }
}