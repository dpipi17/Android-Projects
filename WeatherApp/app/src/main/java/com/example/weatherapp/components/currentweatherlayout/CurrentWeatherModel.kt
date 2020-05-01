package com.example.weatherapp.components.currentweatherlayout

data class CurrentWeatherModel(var cityName : String, var temperature : Int, var timezone : Long, var feelsLike : Int, var dt : Long){
}