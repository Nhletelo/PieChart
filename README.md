# 🧁 Budget Tracker with Pie Chart & XP System (Android - Kotlin)

This Android app helps users manage their expenses within a set budget, while gamifying the experience with a fun XP and leveling system. Expenses are visually tracked using a pie chart, and users gain experience points (XP) for each expense added.

---

## ✨ Features

-  Set **Minimum** and **Maximum** Monthly Budget
-  Add and track **Expenses**
-  Visual **Pie Chart** showing Expenses vs Remaining Budget
-  XP System to **Level Up** with each expense
-  **Animated Badge** appears on level up 🎉
- 
🎯 How It Works
User enters min and max budget.

Adds expenses one by one.

XP increases with each valid expense.

Pie Chart updates to show used vs remaining budget.

When XP reaches 100, user levels up and sees a badge momentarily.

├── app
│   ├── src/main/java/com/example/piechart
│   │   └── MainActivity.kt
│   ├── res/layout
│   │   └── activity_main.xml
│   ├── res/drawable
│   │   └── congrats_badge.png
│   ├── res/values
│   │   └── strings.xml, colors.xml
