package com.example.todoapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.todoapp.ui.MainActivity

class Utils {
    fun hideKeyboard(view: View, activity: MainActivity) {
        val imm =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}