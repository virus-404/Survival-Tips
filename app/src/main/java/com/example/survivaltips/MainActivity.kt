package com.example.survivaltips


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity: AppCompatActivity (){
    private var tipList: ArrayList <Tip> = ArrayList()

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
            intent = Intent(this, HowToActivity::class.java)
            intent.putExtra("tips", TipWrapper(tipList))
            runHowToActivity(intent)
        }

        healthButton.setOnClickListener{
            Toast.makeText(this,"HEALTH",Toast.LENGTH_SHORT).show()
            intent = Intent(this, HealthActivity::class.java)
            runHowToActivity(intent)
        }

        toolsButton.setOnClickListener{
            Toast.makeText(this,"TOOLS",Toast.LENGTH_SHORT).show()
            intent = Intent(this, ToolsActivity::class.java)
            runHowToActivity(intent)
        }
        diaryButton.setOnClickListener{
            Toast.makeText(this,"DIARY",Toast.LENGTH_SHORT).show()
            intent = Intent(this, DiaryActivity::class.java)
            runHowToActivity(intent)
        }

        for (i in 0 .. 22) tipList.add(Tip((65.. 89).random().toChar().toString(), R.drawable.how_to, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))

    }

    fun runHowToActivity(intent: Intent) {

        this.startActivity(intent)
    }

}