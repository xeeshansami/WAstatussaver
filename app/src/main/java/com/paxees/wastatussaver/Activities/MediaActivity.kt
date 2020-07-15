package com.paxees.wastatussaver.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.paxees.wastatussaver.Adapter.ViewPagerAdapter
import com.paxees.wastatussaver.R
import com.paxees.wastatussaver.fragments.OpenMediaFragment
import kotlinx.android.synthetic.main.activity_main.*

class MediaActivity : AppCompatActivity() {
    var mediaFile: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        init()
    }
    fun init() {
        tabs.setupWithViewPager(view_pager)
        if (intent.hasExtra("Media")) {
            mediaFile = intent.getStringExtra("Media")
        }
        toolbar.setTitle("Media")
        mediaFile.let { toolbar.setSubtitle(it?.substring(it.lastIndexOf("/") + 1)) }
        mediaFile.let {
            var fileType = it?.substring(it.lastIndexOf(".") + 1)
            if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
                addTabs(view_pager, "Image")
            } else {
                addTabs(view_pager, "Video")
            }
        }
    }
    private fun addTabs(viewPager: ViewPager, type: String) {
        val adapter = ViewPagerAdapter(getSupportFragmentManager())
        adapter.addFrag(OpenMediaFragment(), type)
        viewPager.adapter = adapter
    }
}