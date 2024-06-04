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
        val word1 = WordCache(1, "123")
        val word2 = WordCache(2, "456")
        val word3 = WordCache(3, "789")
        dao.save(listOf(word1, word2, word3))

        assertEquals(
            word1.word,
            dao.read(1)
        )
        assertEquals(
            word2.word,
            dao.read(2)
        )
        assertEquals(
            word3.word,
            dao.read(3)
        )

        val wordNew1 = WordCache(1, "123")
        val wordNew2 = WordCache(2, "456")
        dao.save(listOf(wordNew1, wordNew2))
        assertEquals(wordNew1.word, dao.read(1))
        assertEquals(wordNew2.word, dao.read(2))
        assertEquals(word3.word, dao.read(3))
    }
}