package com.example.weatherapp.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.weatherapp.fragments.WeatherPageFragment
import com.example.weatherapp.network.dataclasses.City

class WeatherViewPagerAdapter internal constructor(fm: FragmentManager, behaviour: Int) : FragmentStatePagerAdapter(fm, behaviour) {

    private var cityList : List<City> = ArrayList();

    override fun getItem(position: Int): Fragment {
        return WeatherPageFragment.newInstance(cityList[position].name)
    }

    override fun getCount(): Int {
        return cityList.size
    }

    fun setCityList(cityList: List<City>) {
        this.cityList = cityList
        notifyDataSetChanged()
    }
}