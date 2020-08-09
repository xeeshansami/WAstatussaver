package com.paxees.statussaver.forwaapp.Activities

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.paxees.statussaver.forwaapp.R
import com.paxees.statussaver.forwaapp.Utils.IntentUtils
import com.paxees.statussaver.forwaapp.Utils.ToastUtils
import com.paxees.statussaver.forwaapp.Utils.Utils
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.File
import java.util.*


class SettingActivity : AppCompatActivity(), View.OnClickListener {
    var permissions = arrayOf(Manifest.permission.CALL_PHONE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        settingBackBtn.setOnClickListener(View.OnClickListener {
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
                    Utils.gotoActivity(this, HelpActivity::class.java)
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

    fun showCustomDialog() {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_help)
        var phoneCallID = dialog.findViewById<LinearLayout>(R.id.callNumberID)
        var supportEmailID = dialog.findViewById<LinearLayout>(R.id.supportEmailID)
        var officialEmailID = dialog.findViewById<LinearLayout>(R.id.officialEmailID)
        var privacyPolicyID = dialog.findViewById<LinearLayout>(R.id.privacyPolicyBtnID)
        var reviewAndRatingID = dialog.findViewById<LinearLayout>(R.id.ratingAndReviewID)
        phoneCallID.setOnClickListener(View.OnClickListener {
            checkForPermissions(0, "")
            dialog.dismiss()
        })
        supportEmailID.setOnClickListener(View.OnClickListener {
            checkForPermissions(1, "support.paxees@gmail.com")
            dialog.dismiss()
        })
        officialEmailID.setOnClickListener(View.OnClickListener {
            checkForPermissions(1, "info.paxees@gmail.com")
            dialog.dismiss()
        })
        privacyPolicyID.setOnClickListener(View.OnClickListener {
            var uri = Uri.parse("https://paxees-statussaver.blogspot.com/p/status-saver.html");
            startActivity(Intent(ACTION_VIEW, uri));
            dialog.dismiss()
        })
        reviewAndRatingID.setOnClickListener(View.OnClickListener {
            openPlayStore()
            dialog.dismiss()
        })
        dialog.show()
    }

    fun sendWA(number: String) {
        try {
            var uri = Uri.parse("smsto:" + number);
            var i = Intent(Intent.ACTION_SENDTO, uri);
            i.putExtra("sms_body", "Hi: ");
            i.setPackage("com.whatsapp");
            startActivity(i);
        } catch (e: Exception) {
            var uri = Uri.parse("smsto:" + number);
            var i = Intent(Intent.ACTION_SENDTO, uri);
            i.putExtra("sms_body", "Hi: ");
            i.setPackage("com.whatsapp.w4b");
            startActivity(i);
        }
    }

    fun openFBAPP() {
        var facebookUrl = "www.facebook.com/paxees";
        var facebookID = "1469155556529089";

        try {
            var versionCode = getApplicationContext().getPackageManager().getPackageInfo(
                "com.facebook.katana",
                0
            ).versionCode;

            if (!facebookID.isEmpty()) {
                // open the FB app using facebookID (fb://profile/facebookID or fb://page/facebookID)
                var uri = Uri.parse("fb://page/" + facebookID);
                startActivity(Intent(ACTION_VIEW, uri));
            } else if (versionCode >= 3002850 && !facebookUrl.isEmpty()) {
                // open FB app using facebook url
                var uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                startActivity(Intent(ACTION_VIEW, uri));
            } else {
                var uri = Uri.parse(facebookUrl);
                startActivity(Intent(ACTION_VIEW, uri));
            }
        } catch (e: PackageManager.NameNotFoundException) {
            // FB is not installed. Open the browser
            var uri = Uri.parse(facebookUrl);
            startActivity(Intent(ACTION_VIEW, uri));
        }
    }

    fun openPlayStore() {
        var appPackageName = packageName; // getPackageName() from Context or Activity object
        try {
            startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("market://details?id=" + packageName)
                )
            );
        } catch (e: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)
                )
            );
        }
    }

    private fun checkForPermissions(action: Int, email: String) {
        Permissions.check(
            this /*context*/,
            permissions,
            null /*rationale*/,
            null /*options*/,
            object : PermissionHandler() {
                override fun onGranted() { // do your task.
                    if (action == 0) {
                        callPhone("923412030258")
                    } else if (action == 1) {
                        sendEmail(email)
                    } else if (action == 2) {
                        sendSms("923412030258")
                    }
                }

                override fun onDenied(
                    context: Context,
                    deniedPermissions: ArrayList<String>
                ) { // permission denied, block the feature. // will add toast here or custom dialog as per requirement
                    ToastUtils.showToastWith(context, "Permission denied")
                }
            })
    }

    fun callPhone(number: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:" + number)
        callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        startActivity(callIntent)
    }

    fun sendEmail(email: String) {
        IntentUtils.sendEmail(email, this)
    }

    fun sendSms(number: String) {
        val intent_sms = Intent(ACTION_VIEW)
        intent_sms.data = Uri.parse("sms:")
        intent_sms.putExtra("address", number)
        startActivity(intent_sms)
    }
}
