package com.example.unscramblegame.load.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WordCache::class],
    exportSchema = false,
    version = 1
)
abstract class WordsDatabase : RoomDatabase() {

    abstract fun dao(): WordsDao
}