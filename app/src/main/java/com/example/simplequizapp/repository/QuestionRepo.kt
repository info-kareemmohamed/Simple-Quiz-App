package com.example.simplequizapp.repository

import com.example.simplequizapp.model.Question

class QuestionRepo {
    private val questions: List<Question> = listOf(
        Question(
            "What is the capital of France?",
            listOf("London", "Paris", "Berlin"), "Paris"
        ),
        Question(
            "What programming language is used in Android applications?",
            listOf("Java", "Kotlin", "C++"),
            "Kotlin"

        ),
        Question(
            "What is the chemical symbol for water?",
            listOf("H2O", "CO2", "O2"), "H2O"
        ),
    )

    fun getQuestions(): List<Question> = questions


}