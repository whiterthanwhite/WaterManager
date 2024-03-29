package com.bst.watermanager.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bst.watermanager.databinding.ContainerInfoBinding
import com.bst.watermanager.model.Container

class ContainersAdapter(
    private var dataset: List<Container>?,
    private val contAdapListener: ContainerAdapterListener,
    private val parentActivity: Activity
) : RecyclerView.Adapter<ContainersAdapter.ViewHolder>()
{
    private val TAG = "ADAPTER"
    lateinit var listener: ContainerAdapterListener

    interface ContainerAdapterListener {
        fun addVolume(cont: Container)
        fun removeVolume(cont: Container)
        fun deleteContainer(cont: Container)
    }

    class ViewHolder(view: View, contInfoBind: ContainerInfoBinding) :
        RecyclerView.ViewHolder(view)
    {
        val contName: TextView = contInfoBind.contName
        val contVolume: TextView = contInfoBind.contVolume
        val volAdd = contInfoBind.volAdd
        val volRemove = contInfoBind.volRemove
        val contDel = contInfoBind.contDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val contInfoBind = ContainerInfoBinding.inflate(inflater)

        try {
            listener = parent.context as ContainerAdapterListener
        } catch (e: ClassCastException) {
            throw ClassCastException((parent.context.toString() +
                    " must implement NoticeDialogListener"))
        }

        return ViewHolder(contInfoBind.root, contInfoBind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataset?.let {
            listContainers ->
            holder.contName.text = listContainers[position].name
            holder.contVolume.text = "${listContainers[position].volume} ml"

            holder.volAdd.setOnClickListener {
                listener.addVolume(listContainers[position])
            }
            holder.volRemove.setOnClickListener {
                listener.removeVolume(listContainers[position])
            }
            holder.contDel.setOnClickListener {
                listener.deleteContainer(listContainers[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset?.size ?: 0
    }

    fun setContainerList(containers: List<Container>) {
        dataset = containers
        this.notifyDataSetChanged()
    }
}