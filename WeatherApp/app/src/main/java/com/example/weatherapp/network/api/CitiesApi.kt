package com.example.weatherapp.network.api

import com.example.weatherapp.network.dataclasses.City
import retrofit2.Call
import retrofit2.http.GET

interface CitiesApi {

    @GET("all?fields=name")
    fun getAllCities(): Call<List<City>>
}