package com.example.survivaltips


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.survivaltips.howto.Activity as HowTo
import com.example.survivaltips.health.Activity as Health
import com.example.survivaltips.tools.Activity as Tools
import com.example.survivaltips.diary.Activity as Diary


class MainActivity: AppCompatActivity (){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val howToButton: ImageButton = findViewById(R.id.how_to_ic)
        val healthButton: ImageButton = findViewById(R.id.health_ic)
        val toolsButton: ImageButton = findViewById(R.id.tools_ic)
        val diaryButton: ImageButton = findViewById(R.id.diary_ic)

        var intent: Intent


        howToButton.setOnClickListener{
            Toast.makeText(this,"HOW - TO",Toast.LENGTH_SHORT).show()
            intent = Intent(this, HowTo::class.java)
            this.startActivity(intent)
        }

        healthButton.setOnClickListener{
            Toast.makeText(this,"HEALTH",Toast.LENGTH_SHORT).show()
            intent = Intent(this, Health::class.java)
            this.startActivity(intent)
        }

        toolsButton.setOnClickListener{
            Toast.makeText(this,"TOOLS",Toast.LENGTH_SHORT).show()
            intent = Intent(this,Tools::class.java)
            this.startActivity(intent)
        }

        diaryButton.setOnClickListener{
            Toast.makeText(this,"DIARY",Toast.LENGTH_SHORT).show()
            intent = Intent(this, Diary::class.java)
            this.startActivity(intent)
        }
    }

}