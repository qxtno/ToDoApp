package com.example.todoapp.ui.addedit

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.utils.models.Date
import java.util.*

class DatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val args: DatePickerDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val year = args.date?.year ?: calendar.get(Calendar.YEAR)
        val month = args.date?.month?.minus(1) ?: calendar.get(Calendar.MONTH)
        val day = args.date?.day ?: calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            "date",
            Date(dayOfMonth, month.plus(1), year)
        )
    }
}