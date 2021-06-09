package com.example.todoapp.utils.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Date(val day: Int?, val month: Int?, val year: Int?) : Parcelable