package com.example.survivaltips.howto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.survivaltips.R


class AddActivity : AppCompatActivity (){

    private lateinit var title: EditText
    private lateinit var image: ImageButton
    private lateinit var description: EditText

    private var replyIntent: Intent = Intent()

    private val RESULT_LOAD_IMAGE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_how_to)

        title = findViewById(R.id.add_title)
        description = findViewById(R.id.add_description)

        val button: Button = findViewById(R.id.add_button_save)

        button.setOnClickListener {
            replyIntent.setClass(this, Activity::class.java)
            if (title.text.isNotBlank() && description.text.isNotBlank()) {
                replyIntent.putExtra(TITLE, title.text.toString())
                replyIntent.putExtra(DESCRIPTION, description.text.toString())
                replyIntent.putExtra(IMAGE,"Health")
                setResult(RESULT_OK, replyIntent)
            } else {
                setResult(RESULT_CANCELED, replyIntent)
                Toast.makeText(this, "Something is missing :(",Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    companion object {
        const val TITLE = "com.example.android.survivaltip.TITLE"
        const val IMAGE = "com.example.android.survivaltip.IMAGE"
        const val DESCRIPTION = "com.example.android.survivaltip.DESCRIPTION"
    }

}
