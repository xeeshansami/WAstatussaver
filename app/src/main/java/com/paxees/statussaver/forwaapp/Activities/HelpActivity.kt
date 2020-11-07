package com.paxees.statussaver.forwaapp.Activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.paxees.statussaver.forwaapp.R
import com.paxees.statussaver.forwaapp.Utils.IntentUtils
import com.paxees.statussaver.forwaapp.Utils.ToastUtils
import com.paxees.statussaver.forwaapp.Utils.Utils
import kotlinx.android.synthetic.main.activity_help.*
import java.util.*


class HelpActivity : AppCompatActivity(), View.OnClickListener {
    var permissions = arrayOf(Manifest.permission.CALL_PHONE)
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        ads()
        helpBackBtn.setOnClickListener(View.OnClickListener {
            finish()
        })
        callNumberID.setOnClickListener(this)
        supportEmailID.setOnClickListener(this)
        officialEmailID.setOnClickListener(this)
        privacyPolicyBtnHelpID.setOnClickListener(this)
        ratingAndReviewID.setOnClickListener(this)
    }

    fun ads() {
        var mAdView: AdView? = null
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        var adRequest = AdRequest.Builder().build()
        mAdView?.loadAd(adRequest)
        /*Intersitial*/
        mInterstitialAd = InterstitialAd(this@HelpActivity)
        mInterstitialAd.adUnitId =resources!!.getString(R.string.intersitialID)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.callNumberID -> {
                Utils.showDialog(this)
                Handler().postDelayed({
                    checkForPermissions(0, "")
                    Utils.hideLoader()
                }, 1000)

            }
            R.id.supportEmailID -> {
                Utils.showDialog(this)
                Handler().postDelayed({
                    checkForPermissions(1, "support.paxees@gmail.com")
                    Utils.hideLoader()
                }, 1000)

            }
            R.id.officialEmailID -> {
                Utils.showDialog(this)
                Handler().postDelayed({
                    checkForPermissions(1, "info.paxees@gmail.com")
                    Utils.hideLoader()
                }, 1000)
            }
            R.id.privacyPolicyBtnHelpID -> {
                Utils.showDialog(this)
                Handler().postDelayed({
                    var uri = Uri.parse("https://paxees-statussaver.blogspot.com/p/status-saver.html");
                    startActivity(Intent(ACTION_VIEW, uri));
                    Utils.hideLoader()
                }, 1000)
            }
            R.id.ratingAndReviewID -> {
                Utils.showDialog(this)
                Handler().postDelayed({
                    openPlayStore()
                    Utils.hideLoader()
                }, 1000)
            }
        }
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
                // FB is not installed. Open the browser
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
