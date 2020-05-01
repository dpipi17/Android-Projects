package com.example.weatherapp.viewpager.forecastrecyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.viewpager.forecastrecyclerview.cellmodel.CellModel
import com.squareup.picasso.Picasso

class ForecastCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var date : TextView? = null
    private var temperature : TextView? = null
    private var icon : ImageView? = null

    init {
        date = itemView.findViewById(R.id.cell_forecast_date)
        temperature = itemView.findViewById(R.id.cell_forecast_temperature)
        icon = itemView.findViewById(R.id.cell_forecast_icon)
    }

    fun setUpView(model : CellModel) {
        this.date?.text = model.time
        this.temperature?.text = "" + model.temperature + "℃"

        Picasso.get()
            .load("https://openweathermap.org/img/wn/" + model.icon + "@2x.png")
            .into(this.icon)
    }
}