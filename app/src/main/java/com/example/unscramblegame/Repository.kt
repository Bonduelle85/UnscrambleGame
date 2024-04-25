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

    class Base : Repository {
        override fun currentWord(): String {
            TODO("Not yet implemented")
        }

        override fun currentScore(): String {
            TODO("Not yet implemented")
        }

        override fun currentCounter(): String {
            TODO("Not yet implemented")
        }

        override fun sameLength(userInput: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun check(userInput: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun isLast(): Boolean {
            TODO("Not yet implemented")
        }

        override fun next() {
            TODO("Not yet implemented")
        }

        override fun reset() {
            TODO("Not yet implemented")
        }

    }
}