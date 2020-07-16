package com.paxees.wastatussaver.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paxees.wastatussaver.Activities.Dashboard
import com.paxees.wastatussaver.Activities.MediaActivity
import com.paxees.wastatussaver.Models.StatusData
import com.paxees.wastatussaver.R
import com.paxees.wastatussaver.Utils.Utils
import com.paxees.wastatussaver.Utils.setOnitemClickListner
import kotlinx.android.synthetic.main.adatper_listview.view.*
import java.io.File


class RecyclerViewAdapter(
    val items: ArrayList<StatusData>,
    val context: Context,
    fragmentName: String,
    onItemClickListener:setOnitemClickListner
) :
    RecyclerView.Adapter<ViewHolder>() {
    var onItemClickListener: setOnitemClickListner? = null
    var fragmentname: String? = null

    init {
        this.onItemClickListener=onItemClickListener
    }
    override fun getItemCount(): Int {
        return items.size
    }

    init {
        this.fragmentname = fragmentName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.adatper_listview,
                parent,
                false
            )
        )
    }

    // Binds each animal in the ArrayList to a view
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var mediaFIle = items.get(position).media
        holder?.title_ss_tv?.text = items.get(position).filename
        holder?.fileSize?.text = Utils.getFileSize(File(mediaFIle).length())
        holder?.fileDate?.text = Utils.getDate(File(mediaFIle).lastModified())
        mediaFIle?.let {
            Utils.getFileType(it).let { holder?.fileFormat?.text = it }
        }
        Glide.with(context).load("file://" + mediaFIle).into(holder?.thumnails_img)
        if (mediaFIle!!.endsWith(".jpg") || mediaFIle.endsWith(".jpeg") || mediaFIle.endsWith(".png")) {
            holder?.mediaTypeImg.setImageResource(R.drawable.ic_photo_black_24dp)
        } else {
            holder?.mediaTypeImg.setImageResource(R.drawable.ic_video_library_black_24dp)
        }
        holder?.itemView.setOnClickListener(View.OnClickListener {
            var intent = Intent(context, MediaActivity::class.java)
            intent.putExtra("Media", mediaFIle)
            intent.putExtra("fragmentName", this.fragmentname)
            context.startActivity(intent)
        })
        holder?.itemView.setOnLongClickListener(View.OnLongClickListener setOnLongClickListener@{
            onItemClickListener?.onLongClick(holder?.itemView, mediaFIle, position)
            items.removeAt(position)
            notifyDataSetChanged()
            return@setOnLongClickListener true
        })
    }
    private fun confirmSkipUpload(mediaFile:String?,position: Int) {
        val builder = AlertDialog.Builder(context)
        //set title for alert dialog
        builder.setTitle(R.string.dialogTitle)
        //set message for alert dialog
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            deleteVideo(mediaFile)
        }
        //performing cancel action
        /*   builder.setNeutralButton("Cancel"){dialogInterface , which ->
               Toast.makeText(context,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
           }*/
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    private fun deleteVideo(videoUrl: String?) {
        val videoFile = File(videoUrl)
        if (!videoFile.delete()) {
            Log.e("files", "Failed to delete $videoUrl")
        } else {
            MediaScannerConnection.scanFile(
                context,
                arrayOf(videoUrl),
                null
            ) { path, uri ->
                Log.i("ExternalStorage", "Scanned $path:")
                Log.i("ExternalStorage", "-> uri=$uri")
            }
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val title_ss_tv = view.title_ss_tv
    val thumnails_img = view.thumnails_img
    val fileSize = view.fileSize
    val fileDate = view.fileDate
    val fileFormat = view.fileFormat
    val mediaTypeImg = view.mediaTypeImg
}