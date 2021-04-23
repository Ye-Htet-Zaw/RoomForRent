package com.example.roomforrent.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.roomforrent.R
import com.example.roomforrent.adapter.MyTabAdapter
import com.example.roomforrent.fragment.FavouriteFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_bar_layout.view.*


class MainActivity : AppCompatActivity() {

    //Declare arr variable
    var tabNameArr = ArrayList<String>()
    var tabIconArr = ArrayList<Int>()
    var activeTabIconArr = ArrayList<Int>()



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //For Change status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.toolbarbg))

        addTabElement()
        addViewPagerForTab()
        viewPager.adapter?.notifyDataSetChanged()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val currentFragment : Fragment= supportFragmentManager.fragments.last()
                val ft = supportFragmentManager.beginTransaction()
                ft.detach(currentFragment).attach(currentFragment).commit()
                tab?.customView!!.nav_label.setTextColor(resources.getColor(R.color.activeTabTextColor))
                tab.customView!!.nav_icon.setImageResource(activeTabIconArr.get(tab.position))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.customView!!.nav_label.setTextColor(resources.getColor(R.color.black))
                tab.customView!!.nav_icon.setImageResource(tabIconArr.get(tab.position))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun addViewPagerForTab() {
        val adapter = MyTabAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
    }

    private fun addTabElement() {
        //tab Name
        tabNameArr.add("Explore")
        tabNameArr.add("Favourite")
        tabNameArr.add("House")
        tabNameArr.add("Profile")

        //tab icon
        tabIconArr.add(R.drawable.search)
        tabIconArr.add(R.drawable.heart)
        tabIconArr.add(R.drawable.homeicon)
        tabIconArr.add(R.drawable.profile)

        //active tab icon
        activeTabIconArr.add(R.drawable.active_search)
        activeTabIconArr.add(R.drawable.active_heart)
        activeTabIconArr.add(R.drawable.active_homeicon)
        activeTabIconArr.add(R.drawable.active_profile)

        //add tab name and icon
        for (i in 0 until 4) {
            var tablayout = LayoutInflater.from(this).inflate(R.layout.tab_bar_layout, null)
            tablayout.nav_label.text = tabNameArr.get(i)
            tablayout.nav_icon.setImageResource(tabIconArr.get(i))
            tabLayout.addTab(tabLayout.newTab().setCustomView(tablayout))
        }
        tabLayout.nav_label.setTextColor(resources.getColor(R.color.activeTabTextColor))
        tabLayout.nav_icon.setImageResource(R.drawable.active_search)
    }
}
