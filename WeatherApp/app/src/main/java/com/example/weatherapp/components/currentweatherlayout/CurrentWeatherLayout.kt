package com.example.weatherapp.components.currentweatherlayout

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weatherapp.R
import com.example.weatherapp.utils.DateUtils
import java.util.*

class CurrentWeatherLayout : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var cityName : TextView? = null
    private var temperature : TextView? = null
    private var date : TextView? = null
    private var feelsLike : TextView? = null
    private var image : ImageView? = null

    fun initComponents() {
        cityName = this.findViewById(R.id.curr_weather_layout_cityName)
        temperature = this.findViewById(R.id.curr_weather_layout_temperature)
        date = this.findViewById(R.id.current_weather_layout_date)
        feelsLike = this.findViewById(R.id.current_weather_layout_feels_like)
        image = this.findViewById(R.id.current_weather_layout_imageview)
    }

    fun setUpView(model: CurrentWeatherModel) {
        this.cityName?.text = model.cityName
        this.temperature?.text = "" + model.temperature + "℃"
        this.feelsLike?.text = "Percieved " + model.feelsLike + "℃"

        val date = Date((model.dt + model.timezone) * 1000)
        val dateText = DateUtils.getDateText(date, "EEEE dd MMMM hh:mm a")
        this.date?.text = dateText

        if (DateUtils.isDay(date)) {
            image?.setImageResource(R.drawable.ic_sun)
            this.setBackgroundResource(R.drawable.day_gradient)
        } else {
            image?.setImageResource(R.drawable.ic_moon)
            this.setBackgroundResource(R.drawable.night_gradient)
        }
    }

}