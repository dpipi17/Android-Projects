package com.example.weatherapp.viewpager.forecastrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.viewpager.forecastrecyclerview.cellmodel.CellModel

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var cells : List<CellModel> = ArrayList();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cellView: View = LayoutInflater.from(parent.context).inflate(R.layout.cell_forecast, parent, false)
        return ForecastCellViewHolder(cellView)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val forecastCellViewHolder: ForecastCellViewHolder = holder as ForecastCellViewHolder
        forecastCellViewHolder.setUpView(cells[position])
    }

    fun setCells(cells: List<CellModel>) {
        this.cells = cells
        notifyDataSetChanged()
    }

}

