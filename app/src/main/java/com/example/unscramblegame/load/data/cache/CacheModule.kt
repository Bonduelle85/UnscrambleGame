package com.example.unscramblegame.load.data.cache

import android.content.Context
import androidx.room.Room

interface CacheModule {

    fun database(): WordsDatabase

    class Base(context: Context) : CacheModule {

        private val database: WordsDatabase by lazy {
            Room.databaseBuilder(
                context,
                WordsDatabase::class.java,
                WordsDatabase::class.java.simpleName
            ).build()
        }

        override fun database(): WordsDatabase {
            return database
        }
    }
}