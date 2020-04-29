package com.bombadu.mvvm6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bombadu.mvvm6.db.Record

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.RecordHolder>() {
    private var records: List<Record> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAdapter.RecordHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_card_item, parent, false)
        return RecordHolder(itemView)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: RecordAdapter.RecordHolder, position: Int) {
        val currentRecord = records[position]
        holder.textViewDate.text = currentRecord.date
        holder.textViewWeight.text = currentRecord.weight.toString()
        holder.textViewMood.text = currentRecord.mood.toString()

    }

    fun getRecordAt(position: Int) : Record? {
        return records[position]
    }

    fun setRecords(record: List<Record>) {
        this.records = record
        notifyDataSetChanged() //Review if is best
    }


    inner class RecordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewDate: TextView = itemView.findViewById(R.id.date_text_view)
        var textViewWeight: TextView = itemView.findViewById(R.id.weight_text_view)
        var textViewMood: TextView = itemView.findViewById(R.id.mood_text_view)

    }
}