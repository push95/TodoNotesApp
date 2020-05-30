package com.example.todonotesapp.activity.anotherExample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.pagerAdapter.MyTabPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tab_layout.*

class TabLayoutActivity : AppCompatActivity() {

    var toolbar :Toolbar ?=null
    var viewPager :ViewPager ?=null
    var tabLayout : TabLayout ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)
        toolbar = findViewById(R.id.toolbar)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        toolbar!!.setTitle("Tab Layout")
        setSupportActionBar(toolbar)

        val myTabPagerAdapter = MyTabPagerAdapter(supportFragmentManager)
        viewPager!!.adapter =myTabPagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)

        tabLayout!!.setTabTextColors(Color.parseColor("#808080"),Color.parseColor("#ffffff"))


    }
}
