package com.example.studyonline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studyonline.databinding.ActivityProgressBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.example.studyonline.models.SessionItem


class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupProgressBar()
        setupPieChart()
        setupRecyclerView()
    }

    private fun setupProgressBar() {
        val progress = 65
        binding.progressBar.progress = progress
        binding.tvProgressPercent.text = "$progress% Completed"
    }

    private fun setupPieChart() {
        val pieChart: PieChart = binding.pieChart
        val entries = listOf(
            PieEntry(65f, "Completed"),
            PieEntry(35f, "Pending")
        )

        val dataSet = PieDataSet(entries, "Progress")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.centerText = "Study Progress"
        pieChart.setCenterTextSize(14f)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setUsePercentValues(true)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.animateY(1000)
        pieChart.invalidate()
    }

    private fun setupRecyclerView() {
        val dummySessions = listOf(
            SessionItem("Math Study", "April 2, 2025", "1 hr"),
            SessionItem("Physics Practice", "April 3, 2025", "2 hrs"),
            SessionItem("Chemistry Review", "April 4, 2025", "1.5 hrs")
        )

        binding.recyclerViewSessions.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSessions.adapter = SessionAdapter(dummySessions)
    }
}
