package com.example.simplequizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplequizapp.model.Question
import com.example.simplequizapp.repository.QuestionRepo

class QuestionViewModel(private val repository: QuestionRepo) : ViewModel() {
    private val _currentQuestion = MutableLiveData<List<Question>>()
    val currentQuestion: LiveData<List<Question>> = _currentQuestion

    init {
        _currentQuestion.value = repository.getQuestions()
    }


}