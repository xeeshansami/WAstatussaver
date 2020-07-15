package com.paxees.wastatussaver.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.paxees.wastatussaver.Adapter.ViewPagerAdapter
import com.paxees.wastatussaver.R
import com.paxees.wastatussaver.fragments.SavedImages
import com.paxees.wastatussaver.fragments.SavedVideos
import com.paxees.wastatussaver.fragments.StatusFragment
import kotlinx.android.synthetic.main.activity_main.*

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabs.setupWithViewPager(view_pager)
        toolbar.setTitle("WA StatusFragment Saver")
        addTabs(view_pager)
    }
    private fun addTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(getSupportFragmentManager())
        adapter.addFrag(StatusFragment(), "Status")
        adapter.addFrag(SavedImages(), "SavedImages")
        adapter.addFrag(SavedVideos(), "SavedVideos")
        viewPager.adapter = adapter
    }
}