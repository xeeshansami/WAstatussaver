package com.paxees.wastatussaver.Adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paxees.wastatussaver.Models.StatusData
import com.paxees.wastatussaver.R
import com.paxees.wastatussaver.Utils.Utils
import kotlinx.android.synthetic.main.adatper_listview.view.*
import java.io.File

class RecyclerViewAdapter(val items : ArrayList<StatusData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.adatper_listview, parent, false))
    }
    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.title_ss_tv?.text = items.get(position).filename
        holder?.fileSize?.text =Utils.getFileSize( File(items.get(position).media).length())
        holder?.fileDate?.text =Utils.getDate( File(items.get(position).media).lastModified())
        items.get(position).media?.let { Utils.getFileType(it).let {  holder?.fileFormat?.text =it} }
        Glide.with(context).load("file://"+items.get(position).media).into(holder?.thumnails_img)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val title_ss_tv = view.title_ss_tv
    val thumnails_img = view.thumnails_img
    val fileSize = view.fileSize
    val fileDate = view.fileDate
    val fileFormat = view.fileFormat
}