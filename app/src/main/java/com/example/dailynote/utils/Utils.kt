package com.example.dailynote.utils

import org.intellij.lang.annotations.Pattern
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {

    fun dateTimeFormat(data: Long?, pattern: String = "EEE MMM dd yyyy"): String? {
        if (data != null) {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(data)
        }
        return null

    }
}