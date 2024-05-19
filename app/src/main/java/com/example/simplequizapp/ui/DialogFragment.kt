package com.example.simplequizapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.simplequizapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_MESSAGE = "message"
private const val ARG_SCORE = "score"
private const val ARG_ICON = "icon"

class DialogFragment : androidx.fragment.app.DialogFragment() {
    // TODO: Rename and change types of parameters
    private var message: String? = null
    private var score: String? = null
    private var icon: Int? = null
    private var listener: ButtonRestartQuizListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as ButtonRestartQuizListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement ButtonRestartQuizListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            message = it.getString(ARG_MESSAGE)
            score = it.getString(ARG_SCORE)
            icon = it.getInt(ARG_ICON)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_dialog, container, false)

        view.findViewById<TextView>(R.id.F_Dialog_message).text = message
        view.findViewById<TextView>(R.id.F_Dialog_score).text = score
        view.findViewById<ImageView>(R.id.F_Dialog_imge).setBackgroundResource(icon!!)
        view.findViewById<Button>(R.id.F_Dialog_restart_quiz).setOnClickListener { listener?.onClick()}

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(message: String, score: String, icon: Int) =
            DialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MESSAGE, message)
                    putString(ARG_SCORE, score)
                    putInt(ARG_ICON, icon)
                }
            }
    }
}