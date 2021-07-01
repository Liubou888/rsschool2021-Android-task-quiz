package com.rsschool.quiz

class QuestionsDataSource {
    fun getQuestions(): List<Question> {
        return listOf(
            Question(
                1,
                "Which planet in the solar system is closest to Earth?",
                mapOf(0 to "Pluto", 1 to "Mercury", 2 to ".Mars", 3 to "Uranus", 4 to "Saturn"),
                2,
                null
            ),
            Question(
                2,
                "The smallest mammal on Earth?",
                mapOf(
                    0 to "Cat" ,
                    1 to "Etruscan Shrew",
                    2 to "Squirrel",
                    3 to "Mouse",
                    4 to "Rabbit"
                ),
                1,
                null
            ),
            Question(
                3,
                "The most dangerous animal in the world?",
                mapOf(
                    0 to "Elephant",
                    1 to "Lynx",
                    2 to "Wolf",
                    3 to "All of these",
                    4 to "None of these"
                ),
                0,
                null
            ),
            Question(
                4,
                "Which of these plants is a succulent?",
                mapOf(
                    0 to "Morgan's sedum",
                    1 to "Pion",
                    2 to "Onion",
                    3 to "None of the above",
                    4 to "Haretail"
                ),
                0,
                null
            ),
            Question(
                5,
                "The most dangerous plant on the little prince's planet?",
                mapOf(
                    0 to "Oak",
                    1 to "Baobab",
                    2 to "Aloe",
                    3 to "Lemongrass",
                    4 to "Cactus"
                ),
                1,
                null
            )
        )
    }
}
