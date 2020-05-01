package com.example.weatherapp.network.dataclasses

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    var cod : String,
    var main : MainObject,
    var wind : WindObject,
    var sys : SysObject,
    var dt : Long,
    var timezone : Long,
    var visibility : Long
){}

data class ForecastResponse(
    var cod : String,
    var list : List<ListObject>
) {}

data class ListObject(
    var dt : Long,
    var main : MainObject,
    var weather : List<WeatherObject>,
    var wind : WindObject
) {}

data class MainObject(
    var temp : String,
    @SerializedName("feels_like") val feelsLike : String,
    var humidity: Long,
    @SerializedName("grnd_level") var precipitation : Long
) {}

data class WeatherObject(
    var main : String,
    var description : String,
    var icon : String
) {}

data class WindObject(
    var speed : Double
) {}

data class SysObject (
    var sunrise : Long,
    var sunset : Long
) {}