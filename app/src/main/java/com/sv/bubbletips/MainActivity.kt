package com.sv.bubbletips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.sv.bubbletipslib.BubbleTipsTextView

class MainActivity : AppCompatActivity() {

    var buttonClick = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bubbleTipsTR = findViewById<BubbleTipsTextView>(R.id.bubbleTipsTR)
        bubbleTipsTR.show()

        val bubbleTipsTL = findViewById<BubbleTipsTextView>(R.id.bubbleTipsTL)
        bubbleTipsTL.show()

//         val bubbleTipsTC = findViewById<BubbleTipsTextView>(R.id.bubbleTipsTC)
//         bubbleTipsTC.showAWhile(3000)

        val bubbleTipsB = findViewById<BubbleTipsTextView>(R.id.bubbleTipsB)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            when (buttonClick % 3) {
                0 -> {
                    bubbleTipsB.setAnchorViewId(R.id.anchorBL)
                    bubbleTipsB.setArrowPos(BubbleTipsTextView.ArrowPos.BL)
                    bubbleTipsB.show()
                }
                1 -> {
                    bubbleTipsB.setAnchorViewId(R.id.anchorBC)
                    bubbleTipsB.setArrowPos(BubbleTipsTextView.ArrowPos.BC)
                    bubbleTipsB.show()
                }
                2 -> {
                    bubbleTipsB.setAnchorViewId(R.id.anchorBR)
                    bubbleTipsB.setArrowPos(BubbleTipsTextView.ArrowPos.BR)
                    bubbleTipsB.show()
                }

            }
            buttonClick++
        }
    }
}
