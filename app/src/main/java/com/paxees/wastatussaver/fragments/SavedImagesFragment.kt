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
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavedImages.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedImages : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_media, container, false)
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
            var adapter= RecyclerViewAdapter(getFilePaths(),(activity as Dashboard))
            status_rv.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }
    }

    fun getFilePaths(): ArrayList<StatusData> {
        val resultIAV: ArrayList<StatusData> = ArrayList<StatusData>()
        val folder = File(Environment.getExternalStorageDirectory().absolutePath + "/WhatsApp/Media/.Statuses")
        try {
            val allFiles =
                folder.listFiles { dir, name ->
                    (name.endsWith(".jpg")
                            || name.endsWith(".png")
                            || name.endsWith(".jpeg"))
                }
            for (imagePath in allFiles) {
                val path = StatusData()
                path.media = (imagePath.absolutePath)
                path.filename = (imagePath.name)
                resultIAV.add(path)
            }
        } catch (e: NullPointerException) {
        }
        return resultIAV
    }

}
