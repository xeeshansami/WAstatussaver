package com.paxees.wastatussaver.Activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.paxees.wastatussaver.Adapter.ViewPagerAdapter
import com.paxees.wastatussaver.R
import com.paxees.wastatussaver.Utils.Constant
import com.paxees.wastatussaver.fragments.SavedImages
import com.paxees.wastatussaver.fragments.SavedVideos
import com.paxees.wastatussaver.fragments.StatusFragment
import kotlinx.android.synthetic.main.activity_main.*

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    fun init() {
        tabs.setupWithViewPager(view_pager)
        toolbar.setTitle("Status Saver")
        addTabs(view_pager)
        setSupportActionBar(toolbar)
    }
    private fun addTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(getSupportFragmentManager())
        adapter.addFrag(StatusFragment(), "Status")
        adapter.addFrag(SavedImages(), "Saved Images")
        adapter.addFrag(SavedVideos(), "Saved Videos")
        viewPager.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}