package com.example.survivaltips.howto

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.survivaltips.R
import com.example.survivaltips.ddbb.Tip
import kotlin.collections.ArrayList

class TipListAdapter(val context: Context): RecyclerView.Adapter<TipListAdapter.TipViewHolder>(){


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tips = emptyList<Tip>()

    inner class TipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.how_to_image)
        val titleView: TextView = itemView.findViewById(R.id.how_to_title)
        val descriptionView: TextView = itemView.findViewById(R.id.how_to_description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        val itemView: View = inflater.inflate(R.layout.row_how_to, parent, false)
        return TipViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        val current: Tip = tips[position]
        holder.titleView.text = current.title
        holder.descriptionView.text = current.description
        holder.imageView.setImageResource(R.drawable.health)

        holder.imageView.setOnClickListener {
            if (delete) {

            } else {
                val cdd = PopUp(context as Activity, current)
                cdd.show()
            }

        }
    }

    internal fun setTips(tips: List<Tip>) {
        this.tips = tips
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tips.size
    }

    /*

    fun orderItem() {
        tips.sortBy { tip -> tip.title }
        notifyDataSetChanged()
    }

     */

    companion object {
        var delete: Boolean = false
    }

}