package com.example.unscramblegame.load.data.cache

interface CacheDataSource {

    suspend fun save(data: List<String>)

    suspend fun read(index: Int): String

    class Base(
        private val dao: WordsDao,

        ) : CacheDataSource {

        override suspend fun save(data: List<String>) {
            dao.save(data.mapIndexed { index, string ->
                WordCache(id = index, word = string)
            })
        }

        override suspend fun read(index: Int): String {
            return dao.read(index)
        }
    }
}
