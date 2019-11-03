package com.example.survivaltips.health

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.survivaltips.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CheckListAdapter (context: Context): RecyclerView.Adapter<CheckListAdapter.CheckViewHolder>() {

    private val tags: Array<String> = arrayOf("Very high", "High", "Normal", "Low", "Very Low")
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val activity: Activity = context as Activity

    inner class CheckViewHolder(checkView: View):  RecyclerView.ViewHolder(checkView) {
        val title: TextView =  checkView.findViewById(R.id.health_item_title)
        private val chipGroup: ChipGroup = checkView.findViewById(R.id.health_item_chip_group)
        private var lastCheckedId: Int = NO_ID

        init {
            ids.add(chipGroup)
            for (tag: String in tags){
                val chip = Chip(chipGroup.context)
                chip.text = tag
                chip.isClickable = true
                chip.isCheckable = true
                chip.setChipBackgroundColorResource(R.color.colorPrimary)
                chipGroup.addView(chip)
            }
            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                if(checkedId == NO_ID) {
                    activity.progress(increment = false)
                    return@setOnCheckedChangeListener // local return to the caller of the lambda
                }
                lastCheckedId = checkedId
                activity.progress(increment = true)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder {
        val checkView: View = inflater.inflate(R.layout.row_health, parent, false)
        return CheckViewHolder(checkView)
    }

    override fun getItemCount() = checks.size

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) {
        val current: String = checks[position]
        holder.title.text = current
    }

    companion object {
        val ids: ArrayList<ChipGroup> = ArrayList()
        val checks: Array<String> = arrayOf("Temperature:", "Dizziness:", "Tremor:", "Pain:", "Heard pulse:", "Vision clarity:")
    }
}


