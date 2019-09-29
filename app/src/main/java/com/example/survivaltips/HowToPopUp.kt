package com.example.survivaltips

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView


class HowToPopUp (a: Activity, val tip: Tip) : Dialog (a), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.show_how_to)
        val title: TextView = findViewById(R.id.how_to_show_title)
        val image: ImageView = findViewById(R.id.how_to_show_image)
        val description: TextView = findViewById(R.id.how_to_show_description)

        title.text = tip.title
        image.setImageResource(tip.image)
        description.text = tip.description

    }

    override fun onClick(p0: View?) {
        dismiss()
    }

}
