package com.example.todonotesapp.adapter.pagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.todonotesapp.fragment.viewPagerFragment.FragmentOne
import com.example.todonotesapp.fragment.viewPagerFragment.FragmentThree
import com.example.todonotesapp.fragment.viewPagerFragment.FragmentTwo

class MyTabPagerAdapter(manager: FragmentManager) :
    FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val possition :Int=3

    // This method is set our position
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentOne()
            }
            1 -> {
                FragmentTwo()
            }
            else -> {
                FragmentThree()

            }
        } as Fragment

    }

    // and this method will return 3
    override fun getCount(): Int {
        return possition
    }

    // this method set our tabs title
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "one"
            1 ->"two"
            else -> {
               return "Three"
            }
        }
    }
}