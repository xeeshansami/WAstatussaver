package com.paxees.wastatussaver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.paxees.wastatussaver.Adapter.ViewPagerAdapter
import com.paxees.wastatussaver.fragments.SavedImages
import com.paxees.wastatussaver.fragments.SavedVideos
import com.paxees.wastatussaver.fragments.Status
import kotlinx.android.synthetic.main.activity_main.*

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabs.setupWithViewPager(view_pager)
        toolbar.setTitle("WA Status Saver")
        addTabs(view_pager)
    }
    private fun addTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(getSupportFragmentManager())
        adapter.addFrag(Status(), "WA Status")
        adapter.addFrag(SavedImages(), "Saved Images")
        adapter.addFrag(SavedVideos(), "Saved Videos")
        viewPager.adapter = adapter
    }
}