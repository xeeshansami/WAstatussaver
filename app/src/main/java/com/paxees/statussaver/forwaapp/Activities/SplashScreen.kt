package com.paxees.statussaver.forwaapp.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.paxees.statussaver.forwaapp.R
import java.util.*

class SplashScreen : Activity() {
    private lateinit var mInterstitialAd: InterstitialAd
    var permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        MobileAds.initialize(this, resources!!.getString(R.string.appID))
        checkForPermissions()
    }
    private fun checkForPermissions() {
//        sendToDashboard()
        Permissions.check(
            this,
            permissions,
            null ,
            null ,
            object : PermissionHandler() {
                override fun onGranted() { // do your task.
                    mInterstitialAd = InterstitialAd(this@SplashScreen)
                    mInterstitialAd.adUnitId =resources!!.getString(R.string.intersitialID)
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    } else {
                        Log.d("AdsErrors", "The interstitial wasn't loaded yet.")
                    }
                    sendToDashboard()
                }

                @SuppressLint("WrongConstant")
                override fun onDenied(
                    context: Context,
                    deniedPermissions: ArrayList<String>
                ) {
                    Toast.makeText(
                        this@SplashScreen,
                        "Permission denied, please enabled the permissions from setting",
                        1000
                    ).show()
                }
            })
    }
    fun sendToDashboard() {
        Handler().postDelayed({
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }, 2000)

    }
    override fun onStart() {
        super.onStart()
//        checkForPermissions()
    }
}
