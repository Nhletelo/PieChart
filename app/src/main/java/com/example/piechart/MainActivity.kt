package com.example.piechart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.piechart.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var xp = 0
    private var level = 1
    private val maxXP = 100

    private var minBudget = 0f
    private var maxBudget = 0f
    private var totalExpense = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize XP and level UI
        binding.xpProgress.max = maxXP
        updateLevelText()

        // Set budget button logic
        binding.setBudgetBtn.setOnClickListener {
            try {
                minBudget = binding.minBudgetInput.text.toString().toFloat()
                maxBudget = binding.maxBudgetInput.text.toString().toFloat()

                if (minBudget >= maxBudget) {
                    Toast.makeText(this, "Min budget must be less than max budget", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                Toast.makeText(this, "Budget set successfully", Toast.LENGTH_SHORT).show()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid budget input", Toast.LENGTH_SHORT).show()
            }
        }

        // Add expense button logic
        binding.addExpenseBtn.setOnClickListener {
            try {
                if (maxBudget <= 0f) {
                    Toast.makeText(this, "Please set a valid budget first", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val expense = binding.expenseInput.text.toString().toFloat()
                totalExpense += expense

                updateXP(10)
                updatePieChart()

                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Enter a valid expense amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateXP(amount: Int) {
        xp += amount
        if (xp >= maxXP) {
            level++
            xp -= maxXP

            // 🎉 Show the badge when leveling up
            binding.congratsBadge.visibility = View.VISIBLE

            Toast.makeText(this, "Level up! You're now level $level", Toast.LENGTH_SHORT).show()
        } else {
            // 🙈 Hide badge otherwise
            binding.congratsBadge.visibility = View.GONE
        }

        binding.xpProgress.progress = xp
        updateLevelText()
    }

    private fun updateLevelText() {
        binding.levelText.text = "Level: $level (XP: $xp/$maxXP)"
    }

    private fun updatePieChart() {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(totalExpense, "Expenses"))

        val remaining = maxBudget - totalExpense
        if (remaining > 0) {
            entries.add(PieEntry(remaining, "Remaining"))
        }

        val dataSet = PieDataSet(entries, "Budget Overview").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
        }

        val data = PieData(dataSet)
        binding.pieChart.apply {
            this.data = data
            description.isEnabled = false
            setUsePercentValues(true)
            setEntryLabelColor(Color.BLACK)
            invalidate() // refresh chart
        }
    }
}
