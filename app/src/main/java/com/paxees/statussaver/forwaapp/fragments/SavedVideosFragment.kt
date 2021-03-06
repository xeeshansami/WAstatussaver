package com.paxees.statussaver.forwaapp.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.paxees.statussaver.forwaapp.Adapter.RecyclerViewAdapter
import com.paxees.statussaver.forwaapp.Activities.Dashboard
import com.paxees.statussaver.forwaapp.Models.StatusData

import com.paxees.statussaver.forwaapp.R
import com.paxees.statussaver.forwaapp.Utils.Constant
import com.paxees.statussaver.forwaapp.Utils.setOnitemClickListner
import kotlinx.android.synthetic.main.fragment_status.*
import java.io.File
import java.util.ArrayList

class SavedVideosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_media, container, false)
    } override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    override fun onResume() {
        super.onResume()
        val intent = Intent(Constant.SAVE_VIDEO_INTENT)
        LocalBroadcastManager.getInstance((activity as Dashboard).getApplicationContext()).sendBroadcast(intent)
    }
    fun init() {
        LocalBroadcastManager.getInstance((activity as Dashboard).getApplicationContext()).registerReceiver(broadcastReceiver, IntentFilter(
            Constant.SAVE_VIDEO_INTENT)
        )
        val layoutManager = GridLayoutManager(activity, 1)
        status_rv.setLayoutManager(layoutManager)
        if (getFilePaths()!!.size == 0) {
            no_data_found_tv.setVisibility(View.VISIBLE)
            status_rv.setVisibility(View.GONE)
        } else {
            no_data_found_tv.setVisibility(View.GONE)
            status_rv.setVisibility(View.VISIBLE)
            var adapter= RecyclerViewAdapter(getFilePaths(),(activity as Dashboard),"SavedVideosFragment",object :setOnitemClickListner{
                override fun onLongClick(view: View?, mediaFile: String?, position: Int) {
                }

            })
            status_rv.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        }
    }

    fun getFilePaths(): ArrayList<StatusData> {
        val resultIAV: ArrayList<StatusData> = ArrayList<StatusData>()
        val folder = File(Environment.getExternalStorageDirectory().absolutePath + "/Pax-StatusSaver")
        if(!folder.exists()){
            folder.mkdir()
        }
        try {
            val allFiles =
                folder.listFiles { dir, name ->
                    (name.endsWith(".mp4")
                            || name.endsWith(".3gp")
                            || name.endsWith(".avi"))
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
    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Constant.SAVE_VIDEO_INTENT) {
                if (getFilePaths()!!.size == 0) {
                    no_data_found_tv.setVisibility(View.VISIBLE)
                    status_rv.setVisibility(View.GONE)
                } else {
                    no_data_found_tv.setVisibility(View.GONE)
                    status_rv.setVisibility(View.VISIBLE)
                    var adapter= RecyclerViewAdapter(getFilePaths(),(activity as Dashboard),"SavedVideosFragment",object :setOnitemClickListner{
                        override fun onLongClick(view: View?, mediaFile: String?, position: Int) {
                        }
                    })
                    status_rv.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(activity as Dashboard).unregisterReceiver(broadcastReceiver)
        }
    }
}
