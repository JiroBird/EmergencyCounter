package com.github.jirobird.emergencycounter.screens.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.jirobird.emergencycounter.R

class FileListAdapter(private val files: List<String>,
                      private val listener: FileListAdapterClickListener
): RecyclerView.Adapter<FileListElementViewHolder>() {

    interface FileListAdapterClickListener{
        fun onItemClicked(position: Int, fileName:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileListElementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_file_list, parent, false)
        return FileListElementViewHolder(view)
    }

    override fun getItemCount(): Int {
        return files.size
    }

    override fun onBindViewHolder(holder: FileListElementViewHolder, position: Int) {
        holder.bindWith(position, files[position], listener)
    }
}