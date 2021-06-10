package com.example.todoapp.ui.addedit.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsertErrorDialog : DialogFragment() {
    private val args: InsertErrorDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.save_error))
            .setMessage(args.error)
            .setNegativeButton(resources.getText(R.string.cancel)) { _, _ ->
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "retry",
                    false
                )
            }
            .setPositiveButton(resources.getText(R.string.try_again)) { _, _ ->
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "retry",
                    true
                )
            }.create()
}