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
import com.google.android.gms.ads.*
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
    fun intesitialAdsShow(){
        MobileAds.initialize(this,resources!!.getString(R.string.appID))
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = resources!!.getString(R.string.intersitialID)
        mInterstitialAd.loadAd(AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build())
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    Log.d("AdsErrors", "The interstitial wasn't loaded yet.")
                }
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdsErrors", LoadAdError.UNDEFINED_DOMAIN+"The interstitial wasn't loaded yet.")
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        }


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
                    intesitialAdsShow()
                   /* mInterstitialAd = InterstitialAd(this@SplashScreen)
                    mInterstitialAd.adUnitId =resources!!.getString(R.string.intersitialID)
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    } else {
                        Log.d("AdsErrors", "The interstitial wasn't loaded yet.")
                    }*/
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
