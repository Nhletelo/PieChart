package com.example.piechart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.piechart.databinding.ActivityMainBinding
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

        // XP Progress Bar
        binding.xpProgress.max = maxXP
        updateLevelText()

        // Set budget
        binding.setBudgetBtn.setOnClickListener {
            try {
                minBudget = binding.minBudgetInput.text.toString().toFloat()
                maxBudget = binding.maxBudgetInput.text.toString().toFloat()

                if (minBudget >= maxBudget) {
                    Toast.makeText(this, "Min budget must be less than max budget", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                totalExpense = 0f
                updatePieChart()
                updateRemainingBudget()
                Toast.makeText(this, "Budget set successfully", Toast.LENGTH_SHORT).show()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid budget input", Toast.LENGTH_SHORT).show()
            }
        }

        // Add expense
        binding.addExpenseBtn.setOnClickListener {
            try {
                if (maxBudget <= 0f) {
                    Toast.makeText(this, "Please set a valid budget first", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val expense = binding.expenseInput.text.toString().toFloat()
                if (expense <= 0f) {
                    Toast.makeText(this, "Expense must be greater than 0", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val projectedTotal = totalExpense + expense
                if (projectedTotal > maxBudget) {
                    Toast.makeText(this, "Expense exceeds your remaining budget!", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                totalExpense = projectedTotal
                updateXP(10)
                updatePieChart()
                updateRemainingBudget()

                if ((totalExpense / maxBudget) >= 0.9f) {
                    Toast.makeText(this, "⚠️ You're almost out of budget!", Toast.LENGTH_LONG).show()
                }

                Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Enter a valid expense amount", Toast.LENGTH_SHORT).show()
            }
        }

        // Reset button
        binding.resetBtn.setOnClickListener {
            xp = 0
            level = 1
            totalExpense = 0f
            minBudget = 0f
            maxBudget = 0f
            binding.minBudgetInput.text?.clear()
            binding.maxBudgetInput.text?.clear()
            binding.expenseInput.text?.clear()
            updateLevelText()
            updatePieChart()
            updateRemainingBudget()
            Toast.makeText(this, "Data reset successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateXP(amount: Int) {
        xp += amount
        if (xp >= maxXP) {
            level++
            xp -= maxXP

            // Show badge and make it disappear after 3 seconds
            binding.congratsBadge.setImageResource(R.drawable.congrats_badge)
            binding.congratsBadge.visibility = View.VISIBLE

            // Optional animation (fade in)
            binding.congratsBadge.alpha = 0f
            binding.congratsBadge.animate().alpha(1f).setDuration(500).start()

            // Auto-hide after 3 seconds
            binding.congratsBadge.postDelayed({
                binding.congratsBadge.visibility = View.GONE
            }, 3000)

            Toast.makeText(this, "🎉 Level up! You're now level $level", Toast.LENGTH_SHORT).show()
        }

        binding.xpProgress.progress = xp
        updateLevelText()
    }


    private fun updateLevelText() {
        binding.levelText.text = "Level: $level (XP: $xp/$maxXP)"
    }

    private fun updatePieChart() {
        val entries = ArrayList<PieEntry>()

        if (maxBudget <= 0f) {
            binding.pieChart.clear()
            return
        }

        val expensePercent = (totalExpense / maxBudget) * 100f
        val remainingPercent = 100f - expensePercent

        entries.add(PieEntry(expensePercent.coerceIn(0f, 100f), "Expenses"))
        entries.add(PieEntry(remainingPercent.coerceIn(0f, 100f), "Remaining"))

        val dataSet = PieDataSet(entries, "Budget Usage").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextColor = Color.BLACK
            valueTextSize = 14f
        }

        val data = PieData(dataSet)
        binding.pieChart.apply {
            this.data = data
            description.isEnabled = false
            setUsePercentValues(true)
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(14f)
            centerText = "Budget %"
            setCenterTextSize(18f)
            invalidate()
        }
    }

    private fun updateRemainingBudget() {
        val remaining = maxBudget - totalExpense
        binding.remainingBudgetText.text = "Remaining Budget: R${"%.2f".format(remaining)}"
    }
}
