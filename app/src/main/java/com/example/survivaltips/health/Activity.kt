package com.example.survivaltips.health

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View.NO_ID
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.survivaltips.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Thread.sleep
import java.net.URL







class Activity : AppCompatActivity() , Animation.AnimationListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var animBlink: Animation

    @SuppressLint("SetTextI18n")
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)

        val recyclerView: RecyclerView = findViewById (R.id.health_check_list)
        val adapter = CheckListAdapter(this)
        val checkButton: Button = findViewById(R.id.health_check_button)
        progressBar  = findViewById(R.id.health_progressbar)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false

        progressBar.max = CheckListAdapter.checks.size

        animBlink = AnimationUtils.loadAnimation(applicationContext, R.anim.animation)
        animBlink.setAnimationListener(this)

        checkButton.setOnClickListener {
            var all = CheckListAdapter.ids.size
            for (id in CheckListAdapter.ids)  if (id.checkedChipId != NO_ID) all-=1

            if (all != 0) Toast.makeText(this,"Missing {${all}} items from the checklist",LENGTH_SHORT).show()

            else {
                try {
                    it.startAnimation(animBlink) //GlobalScope.async <--opposites--> runBlocking
                    val asyncResult = GlobalScope.async { return@async URL("http://jsonplaceholder.typicode.com/posts").readText()}
                    var i = 0

                    while (!asyncResult.isCompleted ||  i<5) {
                        Log.i("Blink", "iteration number $i")
                        sleep(1000)
                        i+=1
                    }

                    val collectionType = object : TypeToken<Collection<JsonData>>(){}.type
                    val result: Collection<JsonData> =  Gson().fromJson<List<JsonData>>(asyncResult.getCompleted(),collectionType)

                    checkButton.text = "Result is ${result.size}"
                    Toast.makeText(this,"Could connect to the server!",LENGTH_SHORT).show()

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this,"Could not connect to the server!",LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onAnimationEnd(animation: Animation) {
        // Take any action after completing the animation
        val popUp = PopUp(this,fill = false)
        popUp.show()

    }

    override fun onAnimationRepeat(animation: Animation) {}

    override fun onAnimationStart(animation: Animation) {}
    /*

                 popUp.blink()
     */

    fun progress(increment: Boolean) {
        if (increment) progressBar.progress +=1
        else progressBar.progress -=1
    }
}