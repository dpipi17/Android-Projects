package com.example.weatherapp.fragments

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.components.currentweatherlayout.CurrentWeatherLayout
import com.example.weatherapp.components.currentweatherlayout.CurrentWeatherModel
import com.example.weatherapp.components.weatherinfolayout.WeatherInfoLayout
import com.example.weatherapp.components.weatherinfolayout.WeatherInfoModel
import com.example.weatherapp.network.api.ForecastApi
import com.example.weatherapp.network.dataclasses.CurrentWeatherResponse
import com.example.weatherapp.network.dataclasses.ForecastResponse
import com.example.weatherapp.network.dataclasses.ListObject
import com.example.weatherapp.utils.DateUtils
import com.example.weatherapp.viewpager.forecastrecyclerview.RecyclerViewAdapter
import com.example.weatherapp.viewpager.forecastrecyclerview.cellmodel.CellModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class WeatherPageFragment : Fragment() {

    private lateinit var cityName : String
    private var recyclerView : RecyclerView? = null
    private val apiKey : String = "024646fc7cbae519d2f8e85192753bd9"
    private lateinit var progressBar: ProgressBar
    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = arguments?.get("cityName") as String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view : View = inflater.inflate(R.layout.fragment_weather_page, null)
        var currentWeatherLayout : CurrentWeatherLayout = view.findViewById(R.id.curr_weather)
        var weatherInfoLayout : WeatherInfoLayout = view.findViewById(R.id.weather_info)
        currentWeatherLayout.initComponents()
        weatherInfoLayout.initComponents()

        progressBar = view.findViewById(R.id.fragmentProgressBar)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.setLayoutManager(LinearLayoutManager(this.context))
        val adapter = RecyclerViewAdapter()
        recyclerView?.setAdapter(adapter)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: ForecastApi = retrofit.create<ForecastApi>(ForecastApi::class.java)

        progressBar.visibility = View.VISIBLE
        var errorText = getString(R.string.noInfoErrorMessage)
        GlobalScope.launch {
            val forecast = async { forecastCall(service) }
            val currWeatherCall = async { currWeatherCall(service) }
            var forecastResponse : ForecastResponse? = null
            var currWeatherResponse : CurrentWeatherResponse? = null

            if (forecast.await().let {forecastResponse = it; it != null} and currWeatherCall.await().let {currWeatherResponse = it; it != null }) {

                var cells = getCells(forecastResponse!!.list, currWeatherResponse!!.timezone)
                (mContext as Activity).runOnUiThread {
                    progressBar.visibility = View.GONE
                    adapter.setCells(cells)
                    currentWeatherLayout.setUpView(
                        CurrentWeatherModel(
                            cityName,
                            currWeatherResponse!!.main.temp.toDouble().toInt() - 273,
                            currWeatherResponse!!.timezone,
                            currWeatherResponse!!.main.feelsLike.toDouble().toInt() - 273,
                            currWeatherResponse!!.dt
                        )
                    )

                    weatherInfoLayout.setUpView(
                        WeatherInfoModel(
                            currWeatherResponse!!.dt,
                            currWeatherResponse!!.timezone,
                            currWeatherResponse!!.main.precipitation / 100,
                            currWeatherResponse!!.main.humidity / 10,
                            currWeatherResponse!!.wind.speed.toLong(),
                            currWeatherResponse!!.sys.sunrise,
                            currWeatherResponse!!.sys.sunset
                        )
                    )
                }
            } else {
                (mContext as Activity).runOnUiThread {
                    progressBar.visibility = View.GONE
                    view.setBackgroundColor(Color.WHITE);
                    var errorTextView : TextView = view.findViewById(R.id.error_message)
                    errorTextView.text = errorText
                }
            }
        }
        return view
    }

    private suspend fun forecastCall(service : ForecastApi): ForecastResponse? = suspendCoroutine { continuation ->
        service.getForecast(cityName, apiKey).enqueue(object : Callback<ForecastResponse> {
            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                continuation.resume(null)
            }

            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                continuation.resume(if (response.isSuccessful) response.body() else null)
            }
        })
    }

    private suspend fun currWeatherCall(service : ForecastApi): CurrentWeatherResponse? = suspendCoroutine { continuation ->
        service.getCurrentWeather(cityName, apiKey).enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                continuation.resume(null)
            }

            override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {
                continuation.resume(if (response.isSuccessful) response.body() else null)
            }
        })
    }

    private fun getCells (list : List<ListObject>?, timeZone: Long) : List<CellModel> {
        var result : List<CellModel> = ArrayList()
        if (list != null) {
            for (item in list) {
                val weekDayAndTime = DateUtils.getDateText(item.dt + timeZone, "EE hh:mm a")
                result += CellModel(weekDayAndTime, item.weather[0].icon, item.main.temp.toDouble().toInt() - 273)
            }
        }
        return result
    }

    companion object {
        fun newInstance(cityName: String): WeatherPageFragment {
            val args: Bundle = Bundle()
            args.putString("cityName", cityName)
            val fragment = WeatherPageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}