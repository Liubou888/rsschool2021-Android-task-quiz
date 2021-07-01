package com.rsschool.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MainActivityInterface {
    private var currentQuestion = 0
    private var questionsList = listOf<Question>()
    private var resultPercent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startQuiz()
    }

    private fun startQuiz() {
        currentQuestion = 0
        resultPercent = 0
        questionsList = QuestionsDataSource().getQuestions()
        moveToQuestionFragment(questionsList[currentQuestion])
    }

    private fun moveToQuestionFragment(question: Question) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, QuestionFragment.newInstance(question))
            .setReorderingAllowed(true)
            .commit()
    }

    private fun moveToSubmitFragment(percent: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SubmitFragment.newInstance(percent))
            .setReorderingAllowed(true)
            .commit()
    }

    private fun changeTheme(id: Int) {

        when ((id + 5) % 5) {
            0 -> {
                setTheme(R.style.Theme_Quiz_First)
            }
            1 -> {
                setTheme(R.style.Theme_Quiz_Second)
            }
            2 -> {
                setTheme(R.style.Theme_Quiz_Third)
            }
            3 -> {
                setTheme(R.style.Theme_Quiz_Forth)
            }
            4 -> {
                setTheme(R.style.Theme_Quiz_Fifth)
            }
        }

        window.statusBarColor = getThemeColor(android.R.attr.statusBarColor)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun composeEmail(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            type = "plain/text"
            putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
            putExtra(Intent.EXTRA_TEXT, text)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Choose app to share:"))
        }
    }

    override fun moveToNextFragment(checkedAnswer: Int) {
        questionsList[currentQuestion].userAnswer = checkedAnswer
        currentQuestion++
        changeTheme(currentQuestion)
        if (currentQuestion <= questionsList.lastIndex) moveToQuestionFragment(questionsList[currentQuestion])
        else {
            resultPercent = questionsList.count { it.rightAnswer == it.userAnswer } * 100 / questionsList.size
            moveToSubmitFragment(resultPercent)
        }
    }

    override fun moveToPreviousFragment() {
        currentQuestion--
        currentQuestion = currentQuestion.coerceIn(0..questionsList.lastIndex)
        changeTheme(currentQuestion)
        moveToQuestionFragment(questionsList[currentQuestion])
    }

    @SuppressLint("StringFormatInvalid")
    override fun shareResult() {
        val text = buildString{
            append(getString(R.string.result, resultPercent))
            append("\n\n")
            questionsList.forEach { q ->
                append("${q.id}) ${q.question}")
                append("\n")
                append("Your answer: ${q.variantsMap[q.userAnswer]}")
                append("\n\n")
            }
        }
        composeEmail(text)
    }


    override fun finishQuiz() {
        finishAndRemoveTask()
    }

    override fun restartQuiz() {
        startQuiz()
    }
}

fun Context.getThemeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute (attrRes, typedValue, true)
    return typedValue.data
}