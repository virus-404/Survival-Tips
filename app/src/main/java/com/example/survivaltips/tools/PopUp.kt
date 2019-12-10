package com.example.survivaltips.tools

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import com.example.survivaltips.R

class PopUp (context: Context, val image: Bitmap) : Dialog(context){

    lateinit var photo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.show_tools_north)
        photo = findViewById(R.id.tools_north_view)
        photo.setImageBitmap(image)
    }

}