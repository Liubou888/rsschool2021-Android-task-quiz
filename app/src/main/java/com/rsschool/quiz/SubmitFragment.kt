package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.View
import com.rsschool.quiz.R
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentSubmitBinding

private const val PERCENT_BUNDLE_KEY = "PERCENT_BUNDLE_KEY"


class SubmitFragment : Fragment(R.layout.fragment_submit) {
    private var _binding: FragmentSubmitBinding? = null
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
        _binding = FragmentSubmitBinding.bind(view)

        val percent = arguments?.getInt(PERCENT_BUNDLE_KEY)

        percent?.let {
            binding.tvResult.text = getString(R.string.result, it)
        }

        binding.ibShare.setOnClickListener {
            mainActivityInterface.shareResult()
        }

        binding.ibBack.setOnClickListener {
            mainActivityInterface.restartQuiz()
        }

        binding.ibClose.setOnClickListener {
            mainActivityInterface.finishQuiz()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(percent: Int) =
            SubmitFragment().apply {
                arguments = Bundle().apply {
                    putInt(PERCENT_BUNDLE_KEY, percent)
                }
            }
    }
}