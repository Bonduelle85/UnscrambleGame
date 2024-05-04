package com.example.unscramblegame.data

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
        private val permanentStorage: PermanentStorage,
        dataSource: DataSource,
        private val max: Int = 2
    ) : Repository {

        private val list = dataSource.data()

        override fun currentWord(): String {
            return list[permanentStorage.currentIndex()]
        }

        override fun currentScore(): String {
            return permanentStorage.score().toString()
        }

        override fun currentCounter(): String {
            return "${permanentStorage.uiIndex()}/$max"
        }

        override fun sameLength(userInput: String): Boolean {
            return userInput.length == currentWord().length
        }

        override fun check(userInput: String): Boolean {
            return if (currentWord().equals(userInput, ignoreCase = true)) {
                val currentScore = permanentStorage.score()
                val score = currentScore + if (permanentStorage.failed()) 10 else 20
                permanentStorage.saveScore(score)
                true
            } else {
                permanentStorage.saveFailed(true)
                false
            }
        }

        override fun isLast(): Boolean {
            return permanentStorage.uiIndex() == max
        }

        override fun next() {
            permanentStorage.saveCurrentIndex(permanentStorage.currentIndex() + 1)
            if (permanentStorage.currentIndex() == list.size)
                permanentStorage.saveCurrentIndex(0)
            permanentStorage.saveUiIndex(permanentStorage.uiIndex() + 1)
            permanentStorage.saveFailed(false)
        }

        override fun reset() {
            permanentStorage.saveCurrentIndex(permanentStorage.currentIndex() + 1)
            if (permanentStorage.currentIndex() == list.size)
                permanentStorage.saveCurrentIndex(0)
            permanentStorage.saveScore(0)
            permanentStorage.saveUiIndex(1)
            permanentStorage.saveFailed(false)
        }
    }
}

interface DataSource {

    fun data(): List<String>

    class Base : DataSource {
        private val allWords: Set<String> = setOf(
            "animal",
            "auto",
            "anecdote",
            "alphabet",
            "all",
            "awesome",
            "arise",
            "balloon",
            "basket",
            "bench",
            "best",
            "birthday",
            "book",
            "briefcase",
            "camera",
            "camping",
            "candle",
            "cat",
            "cauliflower",
            "chat",
            "children",
            "class",
            "classic",
            "classroom",
            "coffee",
            "colorful",
            "cookie",
            "creative",
            "cruise",
            "dance",
            "daytime",
            "dinosaur",
            "doorknob",
            "dine",
            "dream",
            "dusk",
            "eating",
            "elephant",
            "emerald",
            "eerie",
            "electric",
            "finish",
            "flowers",
            "follow",
            "fox",
            "frame",
            "free",
            "frequent",
            "funnel",
            "green",
            "guitar",
            "grocery",
            "glass",
            "great",
            "giggle",
            "haircut",
            "half",
            "homemade",
            "happen",
            "honey",
            "hurry",
            "hundred",
            "ice",
            "igloo",
            "invest",
            "invite",
            "icon",
            "introduce",
            "joke",
            "jovial",
            "journal",
            "jump",
            "join",
            "kangaroo",
            "keyboard",
            "kitchen",
            "koala",
            "kind",
            "kaleidoscope",
            "landscape",
            "late",
            "laugh",
            "learning",
            "lemon",
            "letter",
            "lily",
            "magazine",
            "marine",
            "marshmallow",
            "maze",
            "meditate",
            "melody",
            "minute",
            "monument",
            "moon",
            "motorcycle",
            "mountain",
            "music",
            "north",
            "nose",
            "night",
            "name",
            "never",
            "negotiate",
            "number",
            "opposite",
            "octopus",
            "oak",
            "order",
            "open",
            "polar",
            "pack",
            "painting",
            "person",
            "picnic",
            "pillow",
            "pizza",
            "podcast",
            "presentation",
            "puppy",
            "puzzle",
            "recipe",
            "release",
            "restaurant",
            "revolve",
            "rewind",
            "room",
            "run",
            "secret",
            "seed",
            "ship",
            "shirt",
            "should",
            "small",
            "spaceship",
            "stargazing",
            "skill",
            "street",
            "style",
            "sunrise",
            "taxi",
            "tidy",
            "timer",
            "together",
            "tooth",
            "tourist",
            "travel",
            "truck",
            "under",
            "useful",
            "unicorn",
            "unique",
            "uplift",
            "uniform",
            "vase",
            "violin",
            "visitor",
            "vision",
            "volume",
            "view",
            "walrus",
            "wander",
            "world",
            "winter",
            "well",
            "whirlwind",
            "x-ray",
            "xylophone",
            "yoga",
            "yogurt",
            "yoyo",
            "you",
            "year",
            "yummy",
            "zebra",
            "zigzag",
            "zoology",
            "zone",
            "zeal"
        )

        override fun data(): List<String> = allWords.toList()
    }
}