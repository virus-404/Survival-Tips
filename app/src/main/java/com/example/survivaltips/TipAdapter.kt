package com.example.survivaltips

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class TipAdapter(var tips: ArrayList<Tip>, val activity: Activity): RecyclerView.Adapter<TipAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.row_how_to, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TipAdapter.ViewHolder, position: Int) {
        val tip: Tip = tips[position]
        val title: TextView = holder.titleView
        val description : TextView = holder.descriptionView
        val image: ImageView = holder.imageView

        title.setText(tip.title)
        description.setText(tip.description)
        image.setImageResource(tip.image)


        image.setOnClickListener {
            val cdd = HowToPopUp(activity,tip)
            cdd.show()

        }
    }

    override fun getItemCount(): Int {
        return tips.size
    }

    fun deleteItem(index: Int) {
        tips.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItem(tip: Tip) {
        tips.add(tip)
        notifyItemInserted(tips.size)
    }

    fun orderItem() {
        tips.sortBy { tip -> tip.title }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.how_to_image)
        val titleView: TextView = itemView.findViewById(R.id.how_to_title)
        val descriptionView: TextView = itemView.findViewById(R.id.how_to_description)

    }
}