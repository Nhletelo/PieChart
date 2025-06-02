package com.example.piechart

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry

class MainActivity : AppCompatActivity() {
    private var xp = 0
    private var level = 1
    private lateinit var levelText: TextView
    private lateinit var xpProgress: ProgressBar
    private lateinit var pieChart: PieChart
    private lateinit var badge: ImageView
    private var minBudget = 0
    private var maxBudget = 0
    private var totalExpense = 0f
    private val expenseEntries = ArrayList<PieEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        levelText = findViewById(R.id.levelText)
        xpProgress = findViewById(R.id.xpProgress)
        val minInput: EditText = findViewById<EditText>(R.id.minBudgetInput)
        val maxInput: EditText = findViewById<EditText>(R.id.maxBudgetInput)
        val SetBudgetBtn: Button = findViewById<Button>(R.id.setBudgetBtn)
        val expenseInput: EditText = findViewById<EditText>(R.id.expenseInput)
        val addexpenseBtn: Button = findViewById<Button>(R.id.addExpenseBtn)
        pieChart = findViewById(R.id.pieChart)
        badge = findViewById(R.id.congratsBadge)

        setBudgetBtn.setOnClickListener {it:View!
            minBudget = minInput.text.toString().toInt()
            maxBudget = maxInput.text.toString().toInt()
            Toast.makeText(this, text:"Budget Set!", Toast.Length_Short).show()
        }

    }
    addexpenseBtn.setOnClickListener+
        }

