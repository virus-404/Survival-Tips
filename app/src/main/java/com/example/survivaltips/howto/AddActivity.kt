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
        image = findViewById(R.id.add_image)
        description = findViewById(R.id.add_description)


        /*
        image.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            val mimeTypes: Array<String> = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes)
            //startActivityForResult(intent, RESULT_LOAD_IMAGE)
        }

         */
         */

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

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            val selectedImage: Uri = data.data!!
            replyIntent.putExtra(IMAGE, selectedImage.toString())
            image.setImageURI(selectedImage)
            Toast.makeText(this, "You have picked Image succesfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_SHORT).show()
        }
    }
    */
    companion object {
        const val TITLE = "com.example.android.survivaltip.TITLE"
        const val IMAGE = "com.example.android.survivaltip.IMAGE"
        const val DESCRIPTION = "com.example.android.survivaltip.DESCRIPTION"
    }

}
