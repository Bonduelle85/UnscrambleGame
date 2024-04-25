package com.example.unscramblegame

interface Repository {

    fun currentWord(): String
    fun currentScore(): String
    fun currentCounter(): String
    fun sameLength(userInput: String): Boolean
    fun check(userInput: String): Boolean
    fun isLast(): Boolean
    fun next()
    fun reset()

    class Base(
        dataSource: DataSource = DataSource(),
        private val max: Int = 2
    ) : Repository {

        private val list = dataSource.allWords.toList()

        private var currentIndex = 0 // 0, 1, 2, 3, 4, 5, ... 1000
        private var uiIndex = 1 // 1, 2, 1, 2, 1, 2 // 1 .. max
        private var score: Int = 0

        private var failed: Boolean = false
        override fun currentWord(): String {
            return list[currentIndex]
        }

        override fun currentScore(): String {
            return score.toString()
        }

        override fun currentCounter(): String {
            return "$uiIndex/$max"
        }

        override fun sameLength(userInput: String): Boolean {
            return userInput.length == currentWord().length
        }

        override fun check(userInput: String): Boolean {
            return if (currentWord().equals(userInput, ignoreCase = true)) {
                score += if (failed) 10 else 20
                true
            } else {
                failed = true
                false
            }
        }

        override fun isLast(): Boolean {
            return uiIndex == max
        }

        override fun next() {
            currentIndex++
            if (currentIndex == list.size)
                currentIndex = 0
            uiIndex++
            failed = false
        }

        override fun reset() {
            currentIndex++
            if (currentIndex == list.size)
                currentIndex = 0
            score = 0
            uiIndex = 1
            failed = false
        }

    }
}