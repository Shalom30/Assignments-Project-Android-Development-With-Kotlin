fun <T : Comparable<T>> maxOf(list: List<T>): T? {
    if (list.isEmpty()) return null
    return list.fold(list[0]) { max, element ->
        if (element > max) element else max
    }
}

fun main() {
    // Test 1: Integers
    val intResult = maxOf(listOf(3, 7, 2, 9))
    println("Max of [3, 7, 2, 9] = $intResult")  // 9

    // Test 2: Strings (lexicographic comparison)
    val strResult = maxOf(listOf("apple", "banana", "kiwi"))
    println("Max of [apple, banana, kiwi] = $strResult")  // kiwi

    // Test 3: Empty list
    val emptyResult = maxOf(emptyList<Int>())
    println("Max of [] = $emptyResult")  // null

    // Test 4: Single element
    val singleResult = maxOf(listOf(42))
    println("Max of [42] = $singleResult")  // 42

    // Test 5: Doubles
    val doubleResult = maxOf(listOf(3.14, 2.71, 1.41, 9.99))
    println("Max of [3.14, 2.71, 1.41, 9.99] = $doubleResult")  // 9.99

    // Test 6: Negative numbers
    val negResult = maxOf(listOf(-10, -3, -7, -1))
    println("Max of [-10, -3, -7, -1] = $negResult")  // -1
}