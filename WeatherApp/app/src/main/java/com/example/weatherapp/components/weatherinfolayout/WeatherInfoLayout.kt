package com.example.weatherapp.components.weatherinfolayout

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weatherapp.R
import com.example.weatherapp.utils.DateUtils
import java.util.*


class WeatherInfoLayout : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var precipitationImage : ImageView? = null
    private var humidityImage : ImageView? = null
    private var windSpeedImage : ImageView? = null
    private var dayAndNightImage : ImageView? = null

    private var precipitationText : TextView? = null
    private var humidityText : TextView? = null
    private var windSpeedText : TextView? = null
    private var dayAndNightText : TextView? = null

    private var precipitationValueText : TextView? = null
    private var humidityValueText : TextView? = null
    private var windSpeedValueText : TextView? = null
    private var dayAndNightValueText : TextView? = null

    fun initComponents() {
        precipitationImage = this.findViewById(R.id.weather_info_layout_precipitation_image)
        humidityImage = this.findViewById(R.id.weather_info_layout_humidity_image)
        windSpeedImage = this.findViewById(R.id.weather_info_layout_wind_speed_image)
        dayAndNightImage = this.findViewById(R.id.weather_info_layout_day_and_night_image)

        precipitationText = this.findViewById(R.id.weather_info_precipitation)
        humidityText = this.findViewById(R.id.weather_info_humidity)
        windSpeedText = this.findViewById(R.id.weather_info_wind_speed)
        dayAndNightText = this.findViewById(R.id.weather_info_day_and_night)

        precipitationValueText = this.findViewById(R.id.weather_info_layout_precipitation_percent)
        humidityValueText = this.findViewById(R.id.weather_info_layout_humidity_percent)
        windSpeedValueText = this.findViewById(R.id.weather_info_layout_wind_speed_value)
        dayAndNightValueText = this.findViewById(R.id.weather_info_layout_day_and_night_value)
    }


    fun setUpView(model: WeatherInfoModel) {
        precipitationValueText?.text = "" + model.precipitation + "%"
        humidityValueText?.text = "" + model.humidity + "%"
        windSpeedValueText?.text = "" + model.windSpeed + "kmh"
        dayAndNightValueText?.text = getDayAndNightText(model.sunrise, model.sunset, model.timezone)

        precipitationText?.text = context.getString(R.string.precipitation)
        humidityText?.text = context.getString(R.string.humidity)
        windSpeedText?.text = context.getString(R.string.windSpeed)
        dayAndNightText?.text = context.getString(R.string.dayAndNight)

        val date = Date((model.dt + model.timezone) * 1000)
        if (DateUtils.isDay(date)) {
            precipitationImage?.setImageResource(R.drawable.ic_drop)
            humidityImage?.setImageResource(R.drawable.ic_humidity)
            windSpeedImage?.setImageResource(R.drawable.ic_flag)
        } else {
            precipitationImage?.setImageResource(R.drawable.night_drop)
            humidityImage?.setImageResource(R.drawable.night_humidity)
            windSpeedImage?.setImageResource(R.drawable.night_flag)
        }
        dayAndNightImage?.setImageResource(R.drawable.ic_day_night)
    }

    private fun getDayAndNightText(sunrise : Long, sunset : Long, timezone : Long) : String {
        val sunriseDate = Date((sunrise + timezone) * 1000)
        val sunsetDate = Date((sunset + timezone) * 1000)
        return DateUtils.getDateText(sunriseDate, "hh:mma") + " " + DateUtils.getDateText(sunsetDate, "hh:mma")
    }

}