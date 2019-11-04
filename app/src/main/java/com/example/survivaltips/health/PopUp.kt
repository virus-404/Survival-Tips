package com.example.survivaltips.health

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.example.survivaltips.R

class PopUp (activity: Activity, private val fill: Boolean) : Dialog(activity) {

    private lateinit var drawView: DrawView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.show_health_progress)


        drawView = findViewById(R.id.health_draw_view)
        drawView.fillFlag=fill
        drawView.invalidate()

        /*
        The soft keyboard causes a View.invalidate()-->View.onDraw() sequence after resizing the Window to sensibly accommodate the 'keyboard'.
        A custom View.onDraw() must leave itself in a state that anticipates this possibility. Such phenomena explains why the app you developed
        and tested on a tablet with a bluetooth keyboard went to the dogs once it reached the real world (-:
         */
    }

    fun blink()  {
        Log.i("Blink",drawView.fillFlag.toString())
        drawView.fillFlag = !drawView.fillFlag
        drawView.invalidate()
    }

}
