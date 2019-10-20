package com.example.survivaltips.howto

import android.app.Activity
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.example.survivaltips.R
import com.example.survivaltips.ddbb.Tip


class PopUp (a: Activity, val tip: Tip) : Dialog (a), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.show_how_to)
        val title: TextView = findViewById(R.id.how_to_show_title)
        val image: ImageView = findViewById(R.id.how_to_show_image)
        val description: TextView = findViewById(R.id.how_to_show_description)

        title.text = tip.title
        image.setImageResource(R.drawable.health)
        description.text = tip.description

    }

    override fun onClick(p0: View?) {
        dismiss()
    }

}
