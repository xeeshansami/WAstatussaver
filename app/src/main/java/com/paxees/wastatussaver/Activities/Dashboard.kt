package com.paxees.wastatussaver.Activities

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.paxees.wastatussaver.Adapter.ViewPagerAdapter
import com.paxees.wastatussaver.R
import com.paxees.wastatussaver.Utils.Constant
import com.paxees.wastatussaver.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class Dashboard : AppCompatActivity() {
    var prevMenuItem: MenuItem? = null
    var savedImagesFragment: SavedImagesFragment? = null
    var savedVideosFragment: SavedVideosFragment? = null
    var statusFragment: StatusFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        viewPager()
    }
    fun init() {
        toolbar.setTitle("Status Saver")
        setSupportActionBar(toolbar)
        tabs.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.statusMenu -> view_pager.setCurrentItem(0)
                    R.id.savedImagesMenu -> view_pager.setCurrentItem(1)
                    R.id.saveVideosMenu -> view_pager.setCurrentItem(2)
                }
                false
            })
        adjustGravity(tabs)
        adjustWidth(tabs)
    }
    fun viewPager() {
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) prevMenuItem!!.setChecked(false) else tabs.getMenu().getItem(
                    0
                ).setChecked(false)
                tabs.getMenu().getItem(position).setChecked(true)
                prevMenuItem = tabs.getMenu().getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        setupViewPager(view_pager)
    }
    private fun adjustGravity(v: View) {
        if (v.id == R.id.smallLabel) {
            val parent = v.parent as ViewGroup
            parent.setPadding(0, 0, 0, 0)

            val params = parent.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.CENTER
            parent.layoutParams = params
        }

        if (v is ViewGroup) {
            val vg = v as ViewGroup

            for (i in 0 until vg.childCount) {
                adjustGravity(vg.getChildAt(i))
            }
        }
    }

    private fun adjustWidth(nav: BottomNavigationView) {
        try {
            val menuViewField = nav.javaClass.getDeclaredField("mMenuView")
            menuViewField.isAccessible = true
            val menuView = menuViewField.get(nav)

            val itemWidth = menuView.javaClass.getDeclaredField("mActiveItemMaxWidth")
            itemWidth.isAccessible = true
            itemWidth.setInt(menuView, Integer.MAX_VALUE)
        } catch (e: NoSuchFieldException) {
            // TODO
        } catch (e: IllegalAccessException) {
            // TODO
        }
    }
    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        statusFragment = StatusFragment()
        savedImagesFragment = SavedImagesFragment()
        savedVideosFragment = SavedVideosFragment()
        adapter.addFrag(statusFragment!!)
        adapter.addFrag(savedImagesFragment!!)
        adapter.addFrag(savedVideosFragment!!)
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