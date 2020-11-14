package com.paxees.statussaver.forwaapp.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.*
import com.paxees.statussaver.forwaapp.Adapter.ViewPagerAdapter
import com.paxees.statussaver.forwaapp.R
import com.paxees.statussaver.forwaapp.fragments.OpenMediaFragment
import kotlinx.android.synthetic.main.activity_main.*

class MediaActivity : AppCompatActivity() {
    var mediaFile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        init()
        ads()
    }
    fun ads() {
        var mAdView: AdView? = null
        MobileAds.initialize(this)
        mAdView = findViewById(R.id.adView)
        var adRequest = AdRequest.Builder().build()
        mAdView?.loadAd(adRequest)
        /*Intersitial*/
        intesitialAdsShow()
    }
    fun intesitialAdsShow(){
        MobileAds.initialize(this,resources!!.getString(R.string.appID))
        var mInterstitialAd: InterstitialAd?=null
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
    fun init() {
        if (intent.hasExtra("Media")) {
            mediaFile = intent.getStringExtra("Media")
        }

        mediaFile.let { toolbar.setSubtitle(it?.substring(it.lastIndexOf("/") + 1)) }
        mediaFile.let {
            var fileType = it?.substring(it.lastIndexOf(".") + 1)
            if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
                toolbar.setTitle("Image Media")
                addTabs(view_pager,"Image")
            } else {
                toolbar.setTitle("VIdeo Media")
                addTabs(view_pager,"VIdeo")
            }
        }
    }
    private fun addTabs(viewPager: ViewPager, text: String) {
        val adapter = ViewPagerAdapter(getSupportFragmentManager())
        adapter.addFrag(OpenMediaFragment())
        viewPager.adapter = adapter
    }
}