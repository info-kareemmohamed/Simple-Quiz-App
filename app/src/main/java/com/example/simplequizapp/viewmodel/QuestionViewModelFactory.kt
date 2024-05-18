package com.example.simplequizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplequizapp.repository.QuestionRepo

class QuestionViewModelFactory(private val questionRepo: QuestionRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuestionViewModel(questionRepo) as T
    }

}