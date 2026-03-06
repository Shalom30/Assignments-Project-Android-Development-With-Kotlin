# Exercise 3 — Complex Data Processing in Kotlin

A Kotlin program that processes a list of `Person` objects to find the average age of people whose names start with a specific letter.

## What it does
- Uses a `data class Person(val name: String, val age: Int)`
- Filters people whose names start with **'A' or 'B'**
- Extracts their ages, calculates the average, and prints it rounded to 1 decimal place
- Extended to a larger student dataset with per-letter group stats (youngest, oldest, average per letter)

## How to Run
1. Open the project folder in **IntelliJ IDEA**
2. Wait for Gradle to sync
3. Open `src/main/kotlin/Main.kt`
4. Click the green ▶ **Run** button next to `fun main()`

## Sample Output
```
Step 1 — Filter names starting with 'A' or 'B':
  → Alice (age 25), Bob (age 30), Anna (age 22), Ben (age 28)

Step 2 — Extract ages: [25, 30, 22, 28]
Step 3 — Calculate average: 105 ÷ 4 = 26.3

➤ Average age of A/B names = 26.3
```

## Tech Stack
- Kotlin 1.9.24
- Gradle 8.8
- JDK 17
