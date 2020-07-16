package com.paxees.wastatussaver.Utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    var listener: setOnitemClickListner? = null
    fun alertBox(
        context: Context?,
        title: String?,
        msg: String?,
        btn1: String?,
        OnClickListener: setOnitemClickListner
    ) {
       /* listener = OnClickListener
        val builder = AlertDialog.Builder(context!!)
        builder.setCancelable(false)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(
            btn1
        ) {
                dialogInterface, i -> listener!!.onLongClick(dialogInterface) }
        val alert = builder.create()
        alert.show()*/
    }

    companion object {
        fun getFileSize(size: Long): String? {
            if (size <= 0) return "0"
            val units = arrayOf("B", "KB", "MB", "GB", "TB")
            val digitGroups =
                (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
            return DecimalFormat("#,##0.#").format(
                size / Math.pow(
                    1024.0,
                    digitGroups.toDouble()
                )
            ) + " " + units[digitGroups]
        }

        fun getDate(getDate: Long): String? {
            val df: DateFormat = SimpleDateFormat("dd-MMM-yyyy h:m a")
            val date = df.format(getDate)
            return date;
        }

        fun getFileType(filePath: String): String {
            return filePath.substring(filePath.lastIndexOf(".") + 1)
        }
    }

}