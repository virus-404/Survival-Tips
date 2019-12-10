package com.example.survivaltips.howto

import android.R.attr.button
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.survivaltips.R
import com.example.survivaltips.ddbb.Tip
import com.example.survivaltips.ddbb.TipViewModel


class Activity : AppCompatActivity(){

    companion object {
        lateinit var tipViewModel: TipViewModel
    }

    val NEW_TIP_ACTIVITY_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to)

        val recyclerView: RecyclerView = findViewById (R.id.content)
        val adapter = TipListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false

        tipViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(this.application))
            .get(TipViewModel::class.java)

        tipViewModel.allTips.observe(this,
            Observer<List<Tip>> { tips ->
                // Update the cached copy of the tips in the adapter.
                tips!!.let { adapter.setTips(tips)}
            })

        val addButton: Button = findViewById(R.id.how_to_add_bt)
        val delButton: Button = findViewById(R.id.how_to_delete_bt)
        val sortButton: ImageButton = findViewById(R.id.how_to_sort_bt)
        val searchBar: TextView = findViewById(R.id.how_to_search_bar)

        addButton.setOnClickListener {
          Toast.makeText(this,"ADD", Toast.LENGTH_SHORT).show()
          intent = Intent(this, AddActivity::class.java)
          startActivityForResult(intent, NEW_TIP_ACTIVITY_REQUEST_CODE)
        }

        delButton.setOnClickListener {
            TipListAdapter.delete = !TipListAdapter.delete

            if (TipListAdapter.delete) {
                delButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            } else {
                delButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            }

        }

        sortButton.setOnClickListener {
            tipViewModel.getAlphabetizedTips()
        }

        searchBar.addTextChangedListener(object : TextWatcher {
          override fun afterTextChanged(s: Editable) {
            if(s.isNotBlank()) tipViewModel.getTipByName(s.toString().toLowerCase())
              else tipViewModel.loadAll()
          }

          override fun beforeTextChanged(s: CharSequence, start: Int,
                                         count: Int, after: Int) {
          }

          override fun onTextChanged(s: CharSequence, start: Int,
                                     before: Int, count: Int) {

          }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (NEW_TIP_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK && data!= null) {
            data.let{
                val description: String = it.getStringExtra(AddActivity.DESCRIPTION)!!
                val title: String = it.getStringExtra(AddActivity.TITLE)!!
                val image: String =  it.getStringExtra(AddActivity.IMAGE)!!
                Toast.makeText(this, "You have created a tip succesfully",Toast.LENGTH_SHORT).show()
                val tip = Tip (null,title, image, description)
                tipViewModel.insert(tip)
            }
        } else {
            Toast.makeText(this,"The tip was not uploaded correctly",Toast.LENGTH_SHORT).show()
        }
    }
}
