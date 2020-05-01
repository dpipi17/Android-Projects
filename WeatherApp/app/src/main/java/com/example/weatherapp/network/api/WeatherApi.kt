package com.example.weatherapp.network.api

import com.example.weatherapp.network.dataclasses.CurrentWeatherResponse
import com.example.weatherapp.network.dataclasses.ForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastApi {

    @GET("data/2.5/forecast")
    fun getForecast(@Query("q") q: String, @Query("appid") appid: String) : Call<ForecastResponse>

    @GET("data/2.5/weather")
    fun getCurrentWeather(@Query("q") q: String, @Query("appid") appid: String) : Call<CurrentWeatherResponse>
}