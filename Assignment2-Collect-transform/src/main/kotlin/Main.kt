/**
 * Exercise 2: Transforming Between Collection Types
 *
 * Task: Given a list of strings, create a map where the keys are the
 * strings and the values are their lengths. Then print only the entries
 * where the length is greater than 4.
 *
 * Extended to a Book Library system — same exact concept (List → Map,
 * filter by length) applied to book titles, making it feel real.
 */

// ─────────────────────────────────────────────────────────────
// HELPER
// ─────────────────────────────────────────────────────────────

fun sectionTitle(title: String) {
    println("\n┌─────────────────────────────────────────────────┐")
    println("│  $title")
    println("└─────────────────────────────────────────────────┘")
}

// ─────────────────────────────────────────────────────────────
// MAIN
// ─────────────────────────────────────────────────────────────

fun main() {

    println("╔══════════════════════════════════════════════════╗")
    println("║     TRANSFORMING BETWEEN COLLECTION TYPES        ║")
    println("╚══════════════════════════════════════════════════╝")

    // ── PART 1: Exact exercise test ───────────────────────────
    sectionTitle("PART 1 ▸ Exact exercise — words → Map<String, Int>")

    val words = listOf("apple", "cat", "banana", "dog", "elephant")
    println("\n  Input list: $words\n")

    // Step 1 — create the map using associateWith
    val wordLengthMap: Map<String, Int> = words.associateWith { it.length }
    println("  Full map (word → length):")
    wordLengthMap.forEach { (word, length) ->
        println("    \"$word\" → $length")
    }

    // Step 2 — filter entries where length > 4
    println("\n  Filtered (length > 4):")
    wordLengthMap
        .filter { (_, length) -> length > 4 }
        .forEach { (word, length) ->
            println("    $word has length $length")
        }
    // Expected output:
    // apple has length 5
    // banana has length 6
    // elephant has length 7

    // ── PART 2: Different threshold values ────────────────────
    sectionTitle("PART 2 ▸ Same map, different length thresholds")

    val moreWords = listOf(
        "hello", "dammy-code", "kotlin", "fun", "list", "map",
        "lambda", "filter", "collection", "programming"
    )
    println("\n  Word list: $moreWords\n")

    val moreWordsMap = moreWords.associateWith { it.length }

    listOf(3, 5, 7).forEach { threshold ->
        val filtered = moreWordsMap.filter { (_, len) -> len > threshold }
        println("  Words with length > $threshold : ${filtered.keys.toList()}")
    }

    // ── PART 3: Map the values — word → length category ───────
    sectionTitle("PART 3 ▸ mapValues — classify each word by length")
    println()

    val categorized: Map<String, String> = moreWordsMap.mapValues { (_, length) ->
        when {
            length <= 3  -> "Short"
            length <= 6  -> "Medium"
            else         -> "Long"
        }
    }

    categorized.forEach { (word, category) ->
        println("  %-15s → %-6s  (%d chars)".format(word, category, moreWordsMap[word]))
    }

    // ── PART 4: Real scenario — Book Library ──────────────────
    sectionTitle("PART 4 ▸ Real scenario — Book Title Library")

    val bookTitles = listOf(
        "Applies Maths",
        "Python Data Science",
        "Real Analysis",
        "Algorithm",
        "The Pragmatic Programmer",
        "Chemistry",
        "Designing Data-Intensive Applications",
        "Data Science"
    )

    println("\n  Books available: ${bookTitles.size}")

    // List → Map<title, wordCount>
    val titleToWordCount: Map<String, Int> = bookTitles
        .associateWith { title -> title.split(" ").size }

    println("\n  Title → Word Count map:")
    titleToWordCount.forEach { (title, count) ->
        println("    %-40s → %d word(s)".format("\"$title\"", count))
    }

    // Filter: only multi-word titles (more than 1 word)
    println("\n  Multi-word titles only (word count > 1):")
    titleToWordCount
        .filter { (_, count) -> count > 1 }
        .forEach { (title, count) ->
            println("    \"$title\"  ($count words)")
        }

    // Also map: title → char length, filter those > 10 chars
    val titleToCharLength = bookTitles.associateWith { it.length }
    println("\n  Titles with more than 10 characters:")
    titleToCharLength
        .filter { (_, len) -> len > 10 }
        .entries
        .sortedByDescending { it.value }
        .forEach { (title, len) ->
            println("    %-40s → %d chars".format(title, len))
        }

    // ── SUMMARY ───────────────────────────────────────────────
    println("\n─".repeat(52))
    println("  Total words processed : ${moreWords.size}")
    println("  Longest word          : ${moreWordsMap.maxByOrNull { it.value }?.key}")
    println("  Shortest word         : ${moreWordsMap.minByOrNull { it.value }?.key}")
    println("  Average word length   : ${"%.1f".format(moreWordsMap.values.average())} chars")
    println("─".repeat(52))

    println("\n✅ Collection transformation complete!\n")
}
