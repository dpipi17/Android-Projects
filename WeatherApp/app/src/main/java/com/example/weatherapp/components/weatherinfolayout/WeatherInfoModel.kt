package com.example.weatherapp.components.weatherinfolayout

data class WeatherInfoModel (
    var dt : Long,
    var timezone : Long,
    var precipitation : Long,
    var humidity : Long,
    var windSpeed : Long,
    var sunrise : Long,
    var sunset : Long
){}