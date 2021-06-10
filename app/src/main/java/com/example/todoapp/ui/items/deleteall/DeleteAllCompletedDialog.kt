package com.example.todoapp.ui.items.deleteall

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.todoapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllCompletedDialog : DialogFragment() {
    private val completedViewModel: DeleteAllCompletedViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.delete_completed))
            .setMessage(resources.getString(R.string.delete_completed_message))
            .setNegativeButton(resources.getText(R.string.no), null)
            .setPositiveButton(resources.getText(R.string.yes)) { _, _ ->
                completedViewModel.onDeleteClick()
            }.create()
}