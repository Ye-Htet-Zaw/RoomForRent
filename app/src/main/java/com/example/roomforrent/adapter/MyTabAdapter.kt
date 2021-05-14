/**
 *
 * Tab Adapter
 *
 * 2021/03/8 KMMN Create New
 *
 * show four tabs in app
 */
package com.example.roomforrent.adapter

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.roomforrent.fragment.*

class MyTabAdapter(
    context: Context,
    supportFragmentManager: FragmentManager,
    tabCount: Int
) : FragmentPagerAdapter(supportFragmentManager) {
    private val share: SharedPreferences = context?.getSharedPreferences(
        "myPreference",
        Context.MODE_PRIVATE
    )!!
    val context = context
    val tabCount: Int = tabCount
    override fun getCount(): Int {
        return tabCount
    }


    override fun getItem(position: Int): Fragment {

        var fragment: Fragment = SearchFragment()
        when (position) {
            0 -> fragment = SearchFragment()
            1 -> fragment = FavouriteFragment()
            2 -> fragment = PostHouseFragment()
            3 -> fragment = LoginProfileFragment()
        }
        return fragment
    }
}
