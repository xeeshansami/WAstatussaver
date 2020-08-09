package com.paxees.statussaver.forwaapp.Utils

import android.content.Context
import android.content.Intent
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat


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


        var progressDialog: TransparentProgressDialog? = null
        fun showDialog(context: Context?) {
            progressDialog = TransparentProgressDialog(context!!)
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        fun hideLoader() {
            if (progressDialog != null && progressDialog!!.isShowing()) {
                progressDialog!!.cancel()
            }
        }

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

        fun gotoActivity(
            context: Context?,
            activity: Class<*>?
        ) {
            var intent = Intent(context, activity)
            context?.startActivity(intent)
        }
    }


}