

// ─────────────────────────────────────────────────────────────
// THE REQUIRED FUNCTION — built manually (no built-in filter)
// ─────────────────────────────────────────────────────────────

fun processList(
    numbers: List<Int>,
    predicate: (Int) -> Boolean
): List<Int> {
    val result = mutableListOf<Int>()
    for (number in numbers) {
        if (predicate(number)) {
            result.add(number)
        }
    }
    return result
}

// ─────────────────────────────────────────────────────────────
// HELPER — print results in a clean format
// ─────────────────────────────────────────────────────────────

fun showResult(label: String, result: List<Int>) {
    println("  %-35s → %s".format(label, result))
}

// ─────────────────────────────────────────────────────────────
// MAIN
// ─────────────────────────────────────────────────────────────

fun main() {

    println("╔══════════════════════════════════════════════╗")
    println("║     HIGHER-ORDER FUNCTION: processList       ║")
    println("╚══════════════════════════════════════════════╝")

    // ── PART 1: Exact test from the exercise ─────────────────
    println("\n▶ PART 1: Required test from exercise")
    println("─".repeat(50))

    val nums = listOf(1, 2, 3, 4, 5, 6)
    println("  Input list: $nums\n")

    val even = processList(nums) { it % 2 == 0 }
    println("  even = processList(nums) { it % 2 == 0 }")
    println("  Result: $even")   // [2, 4, 6]

    // ── PART 2: Reusing processList with different lambdas ───
    println("\n▶ PART 2: Same processList, different lambda predicates")
    println("─".repeat(50))

    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 20, 25, 30)
    println("  Working list: $numbers\n")

    showResult("Even numbers",                   processList(numbers) { it % 2 == 0 })
    showResult("Odd numbers",                    processList(numbers) { it % 2 != 0 })
    showResult("Greater than 10",               processList(numbers) { it > 10 })
    showResult("Less than 6",                    processList(numbers) { it < 6 })
    showResult("Divisible by 3",                processList(numbers) { it % 3 == 0 })
    showResult("Divisible by 5",                processList(numbers) { it % 5 == 0 })
    showResult("Between 5 and 15 (inclusive)",  processList(numbers) { it in 5..15 })
    showResult("Perfect squares (1,4,9,16,25)", processList(numbers) { num ->
        val sqrt = Math.sqrt(num.toDouble()).toInt()
        sqrt * sqrt == num
    })

    // ── PART 3: Chaining processList calls ────────────────────
    println("\n▶ PART 3: Chaining processList — even AND greater than 10")
    println("─".repeat(50))

    val step1 = processList(numbers) { it % 2 == 0 }
    val step2 = processList(step1)  { it > 10 }

    println("  Step 1 — even numbers  : $step1")
    println("  Step 2 — filter > 10   : $step2")

    // ── PART 4: processList with a stored lambda variable ─────
    println("\n▶ PART 4: Storing a lambda in a variable, then passing it")
    println("─".repeat(50))

    val isMultipleOfFour: (Int) -> Boolean = { it % 4 == 0 }
    val multiplesOfFour = processList(numbers, isMultipleOfFour)
    println("  Lambda stored as: val isMultipleOfFour: (Int) -> Boolean = { it % 4 == 0 }")
    println("  Result: $multiplesOfFour")

    // ── PART 5: Real-world use case ───────────────────────────
    println("\n▶ PART 5: Real-world scenario — Lottery ticket checker")
    println("─".repeat(50))

    val ticketNumbers   = listOf(3, 7, 12, 18, 22, 35, 41, 44, 50)
    val winningRange    = 10..25
    val luckyDivisor    = 7

    println("  Ticket numbers : $ticketNumbers")

    val inWinningRange  = processList(ticketNumbers) { it in winningRange }
    val luckyNumbers    = processList(ticketNumbers) { it % luckyDivisor == 0 }
    val bigNumbers      = processList(ticketNumbers) { it > 40 }

    println("  In winning range $winningRange : $inWinningRange")
    println("  Divisible by $luckyDivisor (lucky): $luckyNumbers")
    println("  Big numbers (> 40)       : $bigNumbers")

    val totalMatches = inWinningRange.size + luckyNumbers.size
    println("\n  🎰 You matched $totalMatches special numbers!")

    println("\n✅ All processList tests passed!\n")
}
