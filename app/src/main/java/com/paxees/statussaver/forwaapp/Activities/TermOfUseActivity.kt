package com.paxees.statussaver.forwaapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
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
        mInterstitialAd = InterstitialAd(this@TermOfUseActivity)
        mInterstitialAd.adUnitId =resources!!.getString(R.string.intersitialID)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }
}
