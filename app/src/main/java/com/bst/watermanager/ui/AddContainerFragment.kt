package com.bst.watermanager.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bst.watermanager.R
import com.bst.watermanager.databinding.AddContainerDialogBinding
import com.bst.watermanager.model.Container
import com.bst.watermanager.viewmodel.ContainersViewModel

class AddContainerFragment : DialogFragment() {
    internal lateinit var listener: AddContainerListener

    interface AddContainerListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val addContDial = AddContainerDialogBinding.inflate(inflater)
            val contViewModel : ContainersViewModel by activityViewModels()

            builder.setView(addContDial.root)
                .setPositiveButton(R.string.ok, DialogInterface.OnClickListener {
                    dialog, id ->
                    val name = addContDial.contNameAdd.text.toString()
                    val volume = addContDial.contVolumeAdd.text.toString().toInt()
                    contViewModel.addContainer(Container(name = name, volume = volume))
                    listener.onDialogPositiveClick()
                })
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener {
                    dialog, id ->
                    listener.onDialogNegativeClick()
                    dialog.cancel()
                })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as AddContainerListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString()) +
                "must implement AddContainerListener")
        }
    }
}