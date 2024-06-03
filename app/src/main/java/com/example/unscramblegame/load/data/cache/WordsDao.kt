package com.example.unscramblegame.load.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

    @Query("select word from word_table where id= :index")
    suspend fun read(index: Int): String
    //    OR
    //    @Query("select * from word_table where id= :index")
    //    suspend fun read(index: Int): WordCache

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(words: List<WordCache>)
}