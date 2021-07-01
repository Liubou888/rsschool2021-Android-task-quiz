package com.rsschool.quiz

import java.io.Serializable

data class Question(
    val id: Int,
    val question: String,
    val variantsMap: Map<Int, String>,
    val rightAnswer: Int,
    var userAnswer: Int?
): Serializable
