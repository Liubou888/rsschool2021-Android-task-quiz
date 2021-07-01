package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R.layout
import com.rsschool.quiz.R.string
import com.rsschool.quiz.databinding.FragmentQuizBinding


const val QUESTION_BUNDLE_KEY = "QUESTION_BUNDLE_KEY"

class QuestionFragment : Fragment(layout.fragment_quiz) {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivityInterface: MainActivityInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityInterface) {
            mainActivityInterface = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizBinding.bind(view)

        val question = arguments?.getSerializable(QUESTION_BUNDLE_KEY) as? Question
        question?.let {
            when (question.id) {
                1 -> {
                    binding.toolbar.navigationIcon = null
                    binding.previousButton.isEnabled = false
                }
                5 -> binding.nextButton.text = getString(string.submit)
            }


            with(binding) {
                toolbar.title = getString(string.question_num, question.id)
                tvQuestion.text = question.question
                optionOne.text = question.variantsMap[0]
                optionTwo.text = question.variantsMap[1]
                optionThree.text = question.variantsMap[2]
                optionFour.text = question.variantsMap[3]
                optionFive.text = question.variantsMap[4]


                question.userAnswer?.let{
                    val savedCheckedRadioButton = radioGroup.getChildAt(it) as? RadioButton
                    savedCheckedRadioButton?.isChecked = true
                }

                if (radioGroup.checkedRadioButtonId != -1) {
                    nextButton.isEnabled = true
                }
            }
        }

        with(binding) {
            toolbar.setNavigationOnClickListener {
                mainActivityInterface.moveToPreviousFragment()
            }
            radioGroup.setOnCheckedChangeListener { _, _ ->
                nextButton.isEnabled = true
            }

            previousButton.setOnClickListener {
                mainActivityInterface.moveToPreviousFragment()
            }

            nextButton.setOnClickListener {
                val checkedRadioButtonId = radioGroup.checkedRadioButtonId
                val checkedRadioButton = radioGroup.findViewById(checkedRadioButtonId) as RadioButton
                val checked = radioGroup.indexOfChild(checkedRadioButton)
                Log.d("QuestionFragment_TAG", "checkedId: ${radioGroup.indexOfChild(checkedRadioButton)}")

                mainActivityInterface.moveToNextFragment(checked)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(question: Question): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putSerializable(QUESTION_BUNDLE_KEY, question)
            fragment.arguments = args

            return fragment
        }
    }
}