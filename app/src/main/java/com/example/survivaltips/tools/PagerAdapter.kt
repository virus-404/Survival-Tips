package com.example.survivaltips.tools

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*


class PagerAdapter(manager: FragmentManager, private val numOfTabs: Int) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val tabTitles = LinkedList<CharSequence>()

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }

    fun setPageTitle(title: CharSequence){
        tabTitles.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> CoordinatesFragment()
            else -> CompassFragment()
        }
    }

    override fun getCount() = numOfTabs
}