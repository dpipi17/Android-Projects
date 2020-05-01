package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.weatherapp.network.api.CitiesApi
import com.example.weatherapp.network.dataclasses.City
import com.example.weatherapp.viewpager.WeatherViewPagerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.pager)
        progressBar = findViewById(R.id.progressBar)

        var adapter = WeatherViewPagerAdapter(supportFragmentManager, 0)
        viewPager.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu/rest/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: CitiesApi = retrofit.create<CitiesApi>(CitiesApi::class.java)
        val call = service.getAllCities()

        progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                progressBar.visibility = View.GONE
                if (response.code() == 200) {
                    response.body()?.let { adapter.setCityList(it) }
                }
            }
            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                progressBar.visibility = View.GONE
                var errorTextView : TextView = findViewById(R.id.error_message_main)
                errorTextView.text = getString(R.string.connectionErrorMessage)
            }
        })
    }
}
