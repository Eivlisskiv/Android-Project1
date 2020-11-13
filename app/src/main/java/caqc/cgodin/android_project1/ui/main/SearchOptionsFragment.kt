package caqc.cgodin.android_project1.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import caqc.cgodin.android_project1.R
import caqc.cgodin.android_project1.Utils
import caqc.cgodin.android_project1.activities.MainActivity
import caqc.cgodin.android_project1.activities.Restaurant_Activity
import caqc.cgodin.android_project1.roomdatabase.models.Restaurant
import kotlinx.android.synthetic.main.fragment_search_options.*
import kotlinx.android.synthetic.main.fragment_search_options.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CURRENT_DISTANCE = 5
private const val MAX_DISTANCE = 20

class SearchOptionsFragment : Fragment() {
    var distance: Int
        get() = options_distance_bar.progress
        set(v) {
            options_distance_bar.progress = v;
            options_distance_tb.setText(v.toString())
        }

    fun setUp(tb: EditText, bar: SeekBar){
        tb.setText(bar.progress)
        bar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tb.setText(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        tb.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val i: Int? = s?.toString()?.toInt()
                if(i == null || i < 0 || i > MAX_DISTANCE){
                    tb.error = "!"
                }
                else bar.progress = i;
            }

            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_options, container, false)

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val c = view.context as Restaurant_Activity
        //setUp(c.options_distance_tb, c.options_distance_bar)
    }

    companion object {
        fun newInstance() = SearchOptionsFragment()
    }
}