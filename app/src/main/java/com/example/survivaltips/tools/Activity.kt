package com.example.survivaltips.tools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.survivaltips.R
import com.google.android.material.tabs.TabLayout


class Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools)

        val viewPager: ViewPager = findViewById(R.id.tools_pager)

        val tabLayout: TabLayout = findViewById(R.id.tools_tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("Compass"))
        tabLayout.addTab(tabLayout.newTab().setText("Coordinates"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)
        adapter.setPageTitle("Compass")
        adapter.setPageTitle("Coordinate")
        viewPager.adapter = adapter
    }

}


