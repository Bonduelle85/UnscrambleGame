package com.example.unscramblegame.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unscramblegame.load.data.cache.WordCache
import com.example.unscramblegame.load.data.cache.WordsDao
import com.example.unscramblegame.load.data.cache.WordsDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var dao: WordsDao
    private lateinit var db: WordsDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WordsDatabase::class.java
        ).build()
        dao = db.dao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    /**
     * Save 3
     * check all them
     * save 2
     * check new 2 and third is old one
     */
    @Test
    fun test() = runBlocking {
        dao.save(
            listOf(
                WordCache(1, "123"),
                WordCache(2, "456"),
                WordCache(3, "789"),
            )
        )

        assertEquals(
            WordCache(1, "123").word,
            dao.read(1)
        )
        assertEquals(
            WordCache(2, "456").word,
            dao.read(2)
        )
        assertEquals(
            WordCache(3, "789").word,
            dao.read(3)
        )
    }
}