package com.github.jirobird.emergencycounter.screens.main

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.jirobird.emergencycounter.R
import java.text.SimpleDateFormat
import java.util.*

class FileListElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var nameTextView: TextView? = null
    private var shareButton: ImageButton? = null

    init {
        nameTextView = itemView.findViewById(R.id.tv_file_name)
        shareButton = itemView.findViewById(R.id.ib_share)
    }

    fun bindWith(position:Int, name:String, clickListener: FileListAdapter.FileListAdapterClickListener){
        val timestamp = name.substring(9,name.length - 4).toLong()
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        nameTextView?.text = formatter.format(Date(timestamp))
        shareButton?.setOnClickListener {
            clickListener.onItemClicked(position, name)
        }
    }
}