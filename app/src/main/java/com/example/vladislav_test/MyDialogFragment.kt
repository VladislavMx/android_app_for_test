package com.example.vladislav_test

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {

    companion object {
        const val CAT_ARG = "cat_arg"

        fun newInstance(cat: Cat) = MyDialogFragment().apply {
            arguments = bundleOf(CAT_ARG to cat)
        }
    }

    private val cat
        get() = requireArguments().getParcelable<Cat>(CAT_ARG)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(cat?.text.orEmpty()).setPositiveButton("Закрыть") {
                    dialog, id ->  dialog.cancel()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}