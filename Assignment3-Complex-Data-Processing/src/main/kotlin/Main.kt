/**
 * Exercise 3: Complex Data Processing
 *
 * Task: You have a list of people with names and ages.
 * Find the average age of people whose names start with
 * the letter 'A' or 'B'. Print the result rounded to one decimal place.
 *
 * Extended: same Person data class used in a Student Registration
 * system — filters by multiple letter groups, extracts ages,
 * calculates stats, and formats a full report.
 */

// ─────────────────────────────────────────────────────────────
// DATA CLASS  (exactly as in the exercise)
// ─────────────────────────────────────────────────────────────

data class Person(val name: String, val age: Int)

// ─────────────────────────────────────────────────────────────
// HELPER FUNCTIONS
// ─────────────────────────────────────────────────────────────

/**
 * Returns average age of people whose name starts with
 * any letter in the given set. Returns 0.0 if no match.
 */
fun averageAgeForNames(people: List<Person>, letters: Set<Char>): Double {
    val filtered = people.filter { it.name.first().uppercaseChar() in letters }
    return if (filtered.isEmpty()) 0.0 else filtered.map { it.age }.average()
}

/**
 * Returns people whose name starts with the given letter,
 * sorted by age ascending.
 */
fun peopleStartingWith(people: List<Person>, letter: Char): List<Person> {
    return people
        .filter { it.name.startsWith(letter.toString(), ignoreCase = true) }
        .sortedBy { it.age }
}

fun sectionTitle(title: String) {
    println("\n┌──────────────────────────────────────────────────┐")
    println("│  $title")
    println("└──────────────────────────────────────────────────┘")
}

// ─────────────────────────────────────────────────────────────
// MAIN
// ─────────────────────────────────────────────────────────────

fun main() {

    println("╔════════════════════════════════════════════════╗")
    println("║        COMPLEX DATA PROCESSING — People        ║")
    println("╚════════════════════════════════════════════════╝")

    // ── PART 1: Exact exercise data + solution ────────────────
    sectionTitle("PART 1 ▸ Exact exercise solution")

    val people = listOf(
        Person("Alice",   25),
        Person("Bob",     30),
        Person("Charlie", 35),
        Person("Anna",    22),
        Person("Ben",     28)
    )

    println("\n  All people:")
    people.forEach { p ->
        println("    Person(\"${p.name}\", ${p.age})")
    }

    // Step 1 — Filter
    val filtered = people.filter {
        it.name.startsWith("A") || it.name.startsWith("B")
    }
    println("\n  Step 1 — Filter names starting with 'A' or 'B':")
    filtered.forEach { p -> println("    → ${p.name} (age ${p.age})") }

    // Step 2 — Extract ages
    val ages = filtered.map { it.age }
    println("\n  Step 2 — Extract ages: $ages")

    // Step 3 — Calculate average
    val average = ages.average()
    println("\n  Step 3 — Calculate average: ${ages.sum()} ÷ ${ages.size} = ${"%.1f".format(average)}")

    // Step 4 — Format and print
    println("\n  Step 4 — Rounded to 1 decimal place:")
    println("  ➤  Average age of A/B names = ${"%.1f".format(average)}")
    // Expected: 26.3

    // ── PART 2: Bigger dataset, same approach ─────────────────
    sectionTitle("PART 2 ▸ Larger dataset — Student Registration")

    val students = listOf(
        Person("Alice",    20),
        Person("Andrew",   22),
        Person("Bob",      19),
        Person("Beatrice", 24),
        Person("Charlie",  21),
        Person("Clara",    23),
        Person("David",    20),
        Person("Diana",    22),
        Person("Edward",   25),
        Person("Fiona",    19),
        Person("George",   23),
        Person("Hannah",   21),
        Person("Brian",    18),
        Person("Amy",      26)
    )

    println("\n  Total registered students: ${students.size}")

    // Filter A or B (same logic as exercise)
    val abStudents = students.filter {
        it.name.startsWith("A") || it.name.startsWith("B")
    }
    val abAges    = abStudents.map { it.age }
    val abAverage = abAges.average()

    println("\n  Students with A or B names:")
    abStudents.forEach { p ->
        println("    %-12s → age %d".format(p.name, p.age))
    }
    println("\n  Ages list    : $abAges")
    println("  Average age  : ${"%.1f".format(abAverage)}")

    // ── PART 3: Try different letter groups ───────────────────
    sectionTitle("PART 3 ▸ Average age by different name-letter groups")
    println()

    val groups = mapOf(
        "A & B" to setOf('A', 'B'),
        "C & D" to setOf('C', 'D'),
        "E & F" to setOf('E', 'F'),
        "A, B & C" to setOf('A', 'B', 'C')
    )

    groups.forEach { (label, letters) ->
        val avg = averageAgeForNames(students, letters)
        val matched = students.filter { it.name.first().uppercaseChar() in letters }
        println("  %-12s → avg age: ${"%.1f".format(avg)}  (${matched.size} people: ${matched.map { it.name }})".format(label))
    }

    // ── PART 4: Per-letter breakdown ──────────────────────────
    sectionTitle("PART 4 ▸ Per-letter breakdown — youngest & oldest")
    println()

    listOf('A', 'B', 'C', 'D').forEach { letter ->
        val group = peopleStartingWith(students, letter)
        if (group.isNotEmpty()) {
            println("  Names starting with '$letter' (${group.size} people):")
            println("    Youngest : ${group.first().name} (${group.first().age})")
            println("    Oldest   : ${group.last().name}  (${group.last().age})")
            println("    Avg age  : ${"%.1f".format(group.map { it.age }.average())}")
            println()
        }
    }

    // ── PART 5: Overall summary ───────────────────────────────
    sectionTitle("PART 5 ▸ Full class summary")
    println()
    println("  Total students  : ${students.size}")
    println("  Youngest        : ${students.minByOrNull { it.age }?.name} (age ${students.minByOrNull { it.age }?.age})")
    println("  Oldest          : ${students.maxByOrNull { it.age }?.name} (age ${students.maxByOrNull { it.age }?.age})")
    println("  Class avg age   : ${"%.1f".format(students.map { it.age }.average())}")
    println("  A/B names avg   : ${"%.1f".format(abAverage)}")
    println("  Above class avg : ${students.filter { it.age > students.map { p -> p.age }.average() }.map { it.name }}")

    println("\n✅ Complex data processing complete!\n")
}
