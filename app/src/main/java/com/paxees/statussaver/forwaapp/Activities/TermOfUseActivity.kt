package com.paxees.statussaver.forwaapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.ads.*
import com.paxees.statussaver.forwaapp.R
import kotlinx.android.synthetic.main.activity_privacy_policy.toolbar

class TermOfUseActivity : AppCompatActivity() {
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_of_use)
        ads()
        toolbar.setTitle("")
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
    fun ads() {
        var mAdView: AdView? = null
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        var adRequest = AdRequest.Builder().build()
        mAdView?.loadAd(adRequest)
        /*Intersitial*/
        intesitialAdsShow()
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
}
