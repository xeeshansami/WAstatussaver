package com.paxees.wastatussaver.Activities

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.paxees.wastatussaver.R
import com.paxees.wastatussaver.Utils.ToastUtils
import com.paxees.wastatussaver.Utils.Utils
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.File


class SettingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        toolbar.setTitle("")
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setOnClickListener(View.OnClickListener {
            finish()
        })
        privacyPolicyBtnID.setOnClickListener(this)
        termsOfUseBtnID.setOnClickListener(this)
        helpBtnID.setOnClickListener(this)
        clearFolderBtn.setOnClickListener(this)
        resetBtn.setOnClickListener(this)
        switchBtnNotification.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Utils.showDialog(this)
                Handler().postDelayed({
                    ToastUtils.showToast(this, "Notification enabled")
                    Utils.hideLoader()
                }, 1500)

            } else {
                Utils.showDialog(this)
                Handler().postDelayed({
                    ToastUtils.showToast(this, "Notification disabled")
                    Utils.hideLoader()
                }, 1500)
            }
        })
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.privacyPolicyBtnID -> {
                Utils.showDialog(this)
                Handler().postDelayed({
                    Utils.gotoActivity(this, PrivacyPolicyActivity::class.java)
                    Utils.hideLoader()
                }, 500)
            }
            R.id.termsOfUseBtnID -> {
                Utils.showDialog(this)
                Handler().postDelayed({
                    Utils.gotoActivity(this, TermOfUseActivity::class.java)
                    Utils.hideLoader()
                }, 500)
            }
            R.id.helpBtnID -> {
                Utils.showDialog(this)
                Handler().postDelayed({
                    showCustomDialog("Help messages")
                    Utils.hideLoader()
                }, 1000)

            }
            R.id.clearFolderBtn -> {
                val dir =
                    File(Environment.getExternalStorageDirectory().absolutePath + "/Pax-StatusSaver")
                org.apache.commons.io.FileUtils.deleteDirectory(dir)
                Utils.showDialog(this)
                Handler().postDelayed({
                    ToastUtils.showToast(
                        this,
                        "Folder Pax-StatusSaver files have successfully deleted."
                    )
                    Utils.hideLoader()
                }, 2000)

            }
            R.id.resetBtn -> {
                deleteCache(this)
                Utils.showDialog(this)
                Handler().postDelayed({
                    ToastUtils.showToast(this, "Application cache has been cleared.")
                    Utils.hideLoader()
                }, 2000)
            }
        }
    }
    fun deleteCache(context: Context) {
        try {
            val dir: File = context.getCacheDir()
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }
    fun showCustomDialog(msg: String?) {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custome_dialog_box)
        var text = dialog.findViewById(R.id.text_dialog) as TextView
        text.text = msg
        var dialogButton: Button = dialog.findViewById(R.id.btn_dialog) as Button
        dialogButton.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.show()
    }
}
