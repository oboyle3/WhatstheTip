package com.example.tipme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip =  findViewById(R.id.seekBarTip)
        tvTipPercentLabel = findViewById(R.id.TvTipPercentLabel)
        tvTipAmount= findViewById(R.id.TvTipAmount)
        tvTotalAmount = findViewById(R.id.TVTotalAmount)

        tvTipDescription = findViewById(R.id.tvTipDescription )



        seekBarTip.progress= INITIAL_TIP_PERCENT
        tvTipPercentLabel.text = "$INITIAL_TIP_PERCENT"
        updateTipDescription(INITIAL_TIP_PERCENT)
        seekBarTip.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tvTipPercentLabel.text="$p1%"
                computeTipAndTOtal()
                updateTipDescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }


        })
        etBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"afterTextChanged $p0")
                computeTipAndTOtal()
            }

        })

    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription = when(tipPercent){
          in 0..9 -> "Bad"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        tvTipDescription.text = tipDescription
    }

    private fun computeTipAndTOtal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text=""
            tvTotalAmount.text=""
            return
        }
        //1 get the value of the base and tip perect
        val baseAmount=etBaseAmount.text.toString().toDouble()
        val tipPercent=seekBarTip.progress
        //tip and total
        val tipAmount =baseAmount*tipPercent /100
        val totalAmount = baseAmount + tipAmount
        //update ui
        tvTipAmount.text = "%.2f".format( tipAmount)
        tvTotalAmount.text = "%.2f".format( totalAmount)


    }
}