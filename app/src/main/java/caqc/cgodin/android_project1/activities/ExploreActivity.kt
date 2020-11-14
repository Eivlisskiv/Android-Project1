package caqc.cgodin.android_project1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import caqc.cgodin.android_project1.R
import kotlinx.android.synthetic.main.activity_explore.*

class ExploreActivity : ActivityExtension() {

    val distance: Int
        get() = explore_distance_bar?.progress ?: 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore, arrayOf(R.id.explore_distance_label))
    }

    fun setSeekbar(){
        explore_distance_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                explore_distance_tb.setText(Integer.toString(progress))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    override fun onStart() {
        super.onStart()
        setSeekbar();
        setEditText();
    }

    fun setEditText(){
        explore_distance_tb.setText(Integer.toString(distance));
        explore_distance_tb.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val i: Int = if(s.isNotEmpty()) s.toString().toInt() else 0;
                if(i < 0 || i > 20){
                    explore_distance_tb.error = "!"
                }
                else explore_distance_bar.progress = i;
            }

            override fun afterTextChanged(p0: Editable?) { }
        })
    }
}