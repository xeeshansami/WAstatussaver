package com.paxees.statussaver.forwaapp.fragments

import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable
import com.paxees.statussaver.forwaapp.Activities.MediaActivity
import com.paxees.statussaver.forwaapp.R
import kotlinx.android.synthetic.main.fragment_open_media.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class OpenMediaFragment : Fragment() {
    var mediaController: MediaController? = null
    var mediaFile: String? = null
    var fragmentName: String? = null
    // done popup
    var doneDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_media, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        action()
    }

    fun action() {
        fileSaveBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                mediaFile?.let {
                    if (it.endsWith(".jpg") || it.endsWith(".jpeg") || it.endsWith(".png")) {
                        val bitmap = (imageView.drawable as GlideBitmapDrawable).bitmap
                        popup(bitmap, mediaFile)
                    } else {
                        popup(it)
                    }
                }
            }
        })
    }

    fun init() {
        doneDialog = Dialog((activity as MediaActivity))
        mediaController = MediaController(activity)
        getData()?.let {
            var fileType = it?.substring(it.lastIndexOf(".") + 1)
            if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
                imageView.visibility = View.VISIBLE
                videoView.visibility = View.GONE
                initializeImageView(it)
            } else {
                imageView.visibility = View.GONE
                videoView.visibility = View.VISIBLE
                initializePlayer(it)
            }
        }
    }

    fun initializeImageView(mediaFile: String) {
        Glide.with(context).load(mediaFile).into(imageView)
    }

    fun getData(): String? {
        (activity as MediaActivity).intent.getStringExtra("fragmentName").let {
            if (it.equals("SavedImagesFragment") || it.equals("SavedVideosFragment")) {
                fileSaveBtn.visibility = View.GONE
            }
        }
        if ((activity as MediaActivity).intent.hasExtra("Media")) {
            mediaFile = (activity as MediaActivity).intent.getStringExtra("Media")
        }
        return mediaFile
    }

    fun initializePlayer(mediaFile: String) {
        videoView.setVideoURI(Uri.parse(mediaFile))
        videoView.setMediaController(mediaController)
        videoView.setOnPreparedListener(
            OnPreparedListener { mediaPlayer: MediaPlayer ->
                mediaPlayer.setOnVideoSizeChangedListener { player: MediaPlayer?, width: Int, height: Int ->
                    val controller =
                        MediaController(activity)
                    videoView.setMediaController(controller)
                    controller.setAnchorView(videoView)
                }
            }
        )
        mediaController!!.isEnabled = true
        videoView.start()
    }

    private fun releasePlayer() {
        videoView.stopPlayback()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer();
    }

    fun popup(filename: String) {
        val builder =
            AlertDialog.Builder(activity as MediaActivity)
                .setTitle("Video : $filename")
                .setMessage("Do you want to save this Video?")
                .setNegativeButton(
                    "No"
                ) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(
                    "Yes"
                ) { dialog, which ->
                    saveVideoToInternalStorage(filename)
                    dialog.dismiss()
                }
        val dialog = builder.create()
        dialog.show()
    }

    fun popup(bitmapAs: Bitmap?, filename: String?) {
        val builder =
            AlertDialog.Builder(activity as MediaActivity)
                .setTitle("Photo : $filename")
                .setMessage("Do you want to save this photo?")
                .setNegativeButton(
                    "No"
                ) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(
                    "Yes"
                ) { dialog, which ->
                    saveeFile(bitmapAs, filename)
                    dialog.dismiss()
                }
        val dialog = builder.create()
        dialog.show()
    }

    @Keep
    private fun saveVideoToInternalStorage(filePath: String) {
        try {
            val newfile: File
            val videoAsset: AssetFileDescriptor =
                (activity as MediaActivity).getContentResolver().openAssetFileDescriptor(
                    Uri.parse("file://$filePath"),
                    "r"
                )!!
            val `in` = videoAsset.createInputStream()
            val filepath = Environment.getExternalStorageDirectory()
            val dir = File(filepath.path + "/" + "Pax-StatusSaver" + "/")
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val filename = filePath.substring(filePath.lastIndexOf("/") + 1)
            newfile =
                File(dir, filename + " - " + System.currentTimeMillis() + ".mp4")
            if (newfile.exists()) newfile.delete()
            saveInGalleryForVideo(newfile.path)
            val out: OutputStream = FileOutputStream(newfile)
            // Copy the bits from instream to outstream
            val buf = ByteArray(1024)
            var len: Int
            while (`in`.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
            `in`.close()
            out.close()
            Log.v("", "Copy file successful.")
            //            Toast.makeText(this, "Video has been saved", Toast.LENGTH_SHORT).show();
            donePopup()
        } catch (e: Exception) {
            Toast.makeText(
                (activity as MediaActivity),
                "Something went wrong, Try again later",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }

    @Keep
    fun saveeFile(bmp: Bitmap?, path: String?) {
        var path = path
        val bMap = ThumbnailUtils.createVideoThumbnail(path!!, MediaStore.Video.Thumbnails.MICRO_KIND)
        path = path?.substring(path?.lastIndexOf("/") + 1, path.length - 4)
        val Foldername = "Pax-StatusSaver"
        var dir = File(Environment.getExternalStorageDirectory().toString() ,Foldername)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        try {
            val out = FileOutputStream("$dir/$path.jpeg")
            saveInGalleryForImage("$dir/$path.jpeg")
            bmp?.compress(Bitmap.CompressFormat.JPEG, 100, out) // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            out.flush()
            out.close()
            //            Toast.makeText(this, "Photo has been saved", Toast.LENGTH_SHORT).show();
            donePopup()
        } catch (e: IOException) {
            Toast.makeText(activity, "Something went wrong, Try again later", Toast.LENGTH_SHORT)
                .show()
            e.printStackTrace()
        }
    }

    fun saveInGalleryForVideo(outputFile: String) {
        val content = ContentValues(4)
        content.put(
            MediaStore.Video.VideoColumns.TITLE,
            outputFile.substring(outputFile.lastIndexOf("/") + 1)
        )
        content.put(
            MediaStore.Video.VideoColumns.DATE_ADDED,
            System.currentTimeMillis() / 1000
        )
        content.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        content.put(MediaStore.Video.Media.DATA, outputFile)
        val resolver: ContentResolver = (activity as MediaActivity).getContentResolver()
        resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content)
    }

    fun saveInGalleryForImage(outputFile: String) {
        val content = ContentValues(4)
        content.put(
            MediaStore.Images.Media.TITLE,
            outputFile.substring(outputFile.lastIndexOf("/") + 1)
        )
        content.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        content.put(MediaStore.Images.Media.MIME_TYPE, "image/*")
        content.put(MediaStore.Images.Media.DATA, outputFile)
        val resolver: ContentResolver = (activity as MediaActivity).getContentResolver()
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    fun donePopup() {
        doneDialog?.setContentView(R.layout.done_popup)
        doneDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        doneDialog?.show()
        Handler().postDelayed({
            doneDialog?.dismiss()
        }, 1500)
    }
}
