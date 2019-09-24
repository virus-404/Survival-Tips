package com.example.survivaltips

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HowToActivity : AppCompatActivity(){

    private var tipList: ArrayList <Tip> = ArrayList()
    private var tipQuery: ArrayList <Tip> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to)

        var recyclerView: RecyclerView = findViewById(R.id.content)
        val addButton: Button = findViewById(R.id.how_to_add_bt)
        val delButton: Button = findViewById(R.id.how_to_delete_bt)
        val sortButton: ImageButton = findViewById(R.id.how_to_sort_bt)
        val searchBar: TextView = findViewById(R.id.how_to_search_bar)
        val intent: Intent = getIntent()
        val wrap: TipWrapper= intent.getSerializableExtra("tips") as TipWrapper

        tipList = wrap.tips

        var adapter: TipAdapter = TipAdapter(tipList,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setNestedScrollingEnabled(false)


        addButton.setOnClickListener {
            adapter.addItem(Tip("New", R.drawable.health, "It's new!"))
        }

        delButton.setOnClickListener {
            adapter.deleteItem(0)
        }

        sortButton.setOnClickListener {
            adapter.orderItem()
        }

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                tipQuery.clear()
                tipQuery.addAll(tipList.filter { it.title.startsWith(s.toString())})
                adapter.tips = tipQuery
                adapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

            }

        })
    }

}
