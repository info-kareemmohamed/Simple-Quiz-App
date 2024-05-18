package com.example.simplequizapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.simplequizapp.R
import com.example.simplequizapp.databinding.ActivityMainBinding
import com.example.simplequizapp.model.Question
import com.example.simplequizapp.repository.QuestionRepo
import com.example.simplequizapp.viewmodel.QuestionViewModel
import com.example.simplequizapp.viewmodel.QuestionViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var questionViewModel: QuestionViewModel
    private var score: Int = 0
    private var index: Int = 0
    private var isScreenLocked = false
    private lateinit var questions: List<Question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        getDataFromViewModel()
        setOnClick()


    }


    private fun clear() {
        clearBackgroundColor()
        index = 0; score = 0;isScreenLocked = false
        loadQuestion()
    }

    private fun getDataFromViewModel() {
        questionViewModel.currentQuestion.observe(this) {
            questions = it

            if (questions.isNotEmpty()) {
                loadQuestion()
            }
        }
    }

    private fun setOnClick() {
        binding.MainOption1.setOnClickListener(this)
        binding.MainOption2.setOnClickListener(this)
        binding.MainOption3.setOnClickListener(this)
        binding.MainRestartQuiz.setOnClickListener(this)

    }

    private fun loadQuestion() {
        if (index < questions.size) {
            binding.MainQuestion.text = questions[index].question
            binding.MainOption1.text = questions[index].option[0]
            binding.MainOption2.text = questions[index].option[1]
            binding.MainOption3.text = questions[index].option[2]
        } else {
             var icon:Int
             var message:String
             if(score>questions.size/2){icon=R.drawable.winner;message="Winner"}else{icon=R.drawable.sad;message="Loser"}
            val dialogFragment = DialogFragment.newInstance(message, score.toString(), icon)
            dialogFragment.show(supportFragmentManager, "dialog_fragment_tag")
            clear()
        }
    }

    private fun clearBackgroundColor() {
        binding.MainOption1.setBackgroundColor(ContextCompat.getColor(this, R.color.navyBlue))
        binding.MainOption2.setBackgroundColor(ContextCompat.getColor(this, R.color.navyBlue))
        binding.MainOption3.setBackgroundColor(ContextCompat.getColor(this, R.color.navyBlue))
    }


    private fun setAllWrang() {
        binding.MainOption1.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        binding.MainOption2.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        binding.MainOption3.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
    }


    private fun setupViewModel() {
        val questionRepo = QuestionRepo()
        val viewModelFactory = QuestionViewModelFactory(questionRepo)

        questionViewModel = ViewModelProvider(this, viewModelFactory)
            .get(QuestionViewModel::class.java)
    }

    private fun getAnswer() {
        if (binding.MainOption1.text.equals(questions[index].answer)) {
            binding.MainOption1.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
        } else if (binding.MainOption2.text.equals(questions[index].answer)) {
            binding.MainOption2.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
        } else
            binding.MainOption3.setBackgroundColor(ContextCompat.getColor(this, R.color.green))

    }

    override fun onClick(v: View?) {
        if (!isScreenLocked && index < questions.size) {
            val button = v as Button
            when (button.id) {
                R.id.Main_option1, R.id.Main_option2, R.id.Main_option3 -> {
                    setAllWrang()
                    if (button.text.equals(questions[index].answer)) {

                        button.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                        score++
                    } else {
                        getAnswer()

                    }
                    index++
                    isScreenLocked = true
                    CoroutineScope(Dispatchers.Main).launch {

                        delay(2000)

                        clearBackgroundColor()
                        loadQuestion()
                        isScreenLocked = false
                    }


                }

                R.id.Main_restart_quiz -> clear()
            }
        }
    }
}