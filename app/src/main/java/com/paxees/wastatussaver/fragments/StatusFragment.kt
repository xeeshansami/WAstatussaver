package com.paxees.wastatussaver.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.paxees.wastatussaver.Adapter.RecyclerViewAdapter
import com.paxees.wastatussaver.Activities.Dashboard
import com.paxees.wastatussaver.Models.StatusData
import com.paxees.wastatussaver.R
import kotlinx.android.synthetic.main.fragment_status.*
import java.io.File
import java.util.*

class StatusFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        val layoutManager = GridLayoutManager(activity, 1)
        status_rv.setLayoutManager(layoutManager)
        if (getFilePaths()!!.size == 0) {
            no_data_found_tv.setVisibility(View.VISIBLE)
        } else {
            var adapter = RecyclerViewAdapter(getFilePaths(), (activity as Dashboard))
            status_rv.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }
    }

    fun getFilePaths(): ArrayList<StatusData> {
        val resultIAV: ArrayList<StatusData> = ArrayList<StatusData>()
        val whatsappNormalFolder =
            File(Environment.getExternalStorageDirectory().absolutePath + "/WhatsApp/Media/.Statuses")
        val whatsappBusinessFolder =
            File(Environment.getExternalStorageDirectory().absolutePath + "/WhatsApp Business/Media/.Statuses")
        if (whatsappNormalFolder.exists() && whatsappBusinessFolder.exists()) {
            try {
                /*For Normal Whatsapp*/
                val allFilesOfWhatsappNormalFolder = whatsappNormalFolder.listFiles { dir, name ->
                    (name.endsWith(".mp4")
                            || name.endsWith(".3gp")
                            || name.endsWith(".jpeg")
                            || name.endsWith(".jpg")
                            || name.endsWith(".png")
                            || name.endsWith(".mpeg"))
                }
                for (imagePath in allFilesOfWhatsappNormalFolder) {
                    val path = StatusData()
                    path.media = (imagePath.absolutePath)
                    path.filename = (imagePath.name)
                    resultIAV.add(path)
                }

                /*For Business Whatsapp*/
                val allFilesWhatsappBusinessFolder =
                    whatsappBusinessFolder.listFiles { dir, name ->
                        (name.endsWith(".mp4")
                                || name.endsWith(".3gp")
                                || name.endsWith(".jpeg")
                                || name.endsWith(".jpg")
                                || name.endsWith(".png")
                                || name.endsWith(".mpeg"))
                    }
                for (imagePath in allFilesWhatsappBusinessFolder) {
                    val path = StatusData()
                    path.media = (imagePath.absolutePath)
                    path.filename = (imagePath.name)
                    resultIAV.add(path)
                }
            } catch (e: NullPointerException) {
            }
        } else {
            if (whatsappNormalFolder.exists()) {
                try {
                    val allFiles =
                        whatsappNormalFolder.listFiles { dir, name ->
                            (name.endsWith(".mp4")
                                    || name.endsWith(".3gp")
                                    || name.endsWith(".jpeg")
                                    || name.endsWith(".jpg")
                                    || name.endsWith(".png")
                                    || name.endsWith(".mpeg"))
                        }
                    for (imagePath in allFiles) {
                        val path = StatusData()
                        path.media = (imagePath.absolutePath)
                        path.filename = (imagePath.name)
                        resultIAV.add(path)
                    }
                } catch (e: NullPointerException) {
                }
            } else {
                try {
                    val allFiles =
                        whatsappBusinessFolder.listFiles { dir, name ->
                            (name.endsWith(".mp4")
                                    || name.endsWith(".3gp")
                                    || name.endsWith(".jpeg")
                                    || name.endsWith(".jpg")
                                    || name.endsWith(".png")
                                    || name.endsWith(".mpeg"))
                        }
                    for (imagePath in allFiles) {
                        val path = StatusData()
                        path.media = (imagePath.absolutePath)
                        path.filename = (imagePath.name)
                        resultIAV.add(path)
                    }
                } catch (e: NullPointerException) {
                }
            }
        }
        return resultIAV
    }
}
