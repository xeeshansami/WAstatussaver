package com.paxees.statussaver.forwaapp.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.AssetManager
import android.os.Bundle
import android.os.Environment
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.paxees.statussaver.forwaapp.Activities.Dashboard
import com.paxees.statussaver.forwaapp.Adapter.RecyclerViewAdapter
import com.paxees.statussaver.forwaapp.Models.StatusData
import com.paxees.statussaver.forwaapp.R
import com.paxees.statussaver.forwaapp.Utils.Constant
import com.paxees.statussaver.forwaapp.Utils.setOnitemClickListner
import kotlinx.android.synthetic.main.fragment_status.*
import java.io.File
import java.util.*


class StatusFragment : Fragment() {
    var adapter: RecyclerViewAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false)
    }
    companion object {
        val wAAPNormalFolder = "WhatsApp/Media/.Statuses"
        val wAPPBusinessFolder = "WhatsApp Business/Media/.Statuses"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        LocalBroadcastManager.getInstance((activity as Dashboard).getApplicationContext())
            .registerReceiver(broadcastReceiver, IntentFilter(Constant.SAVE_STATUS_INTENT))
        val layoutManager = GridLayoutManager(activity, 1)
        status_rv.setLayoutManager(layoutManager)
        if (getFilePaths()!!.size == 0) {
            no_data_found_tv.setVisibility(View.VISIBLE)
            status_rv.setVisibility(View.GONE)
        } else {
            no_data_found_tv.setVisibility(View.GONE)
            status_rv.setVisibility(View.VISIBLE)
            adapter = RecyclerViewAdapter(
                getFilePaths(),
                (activity as Dashboard),
                "StatusesFragment",
                object : setOnitemClickListner {
                    override fun onLongClick(view: View?, mediaFile: String?, position: Int) {

                    }

                })
            status_rv.setAdapter(adapter)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(Constant.SAVE_STATUS_INTENT)
        LocalBroadcastManager.getInstance((activity as Dashboard).getApplicationContext())
            .sendBroadcast(intent)
    }

    fun getFilePaths(): ArrayList<StatusData> {
        val resultIAV: ArrayList<StatusData> = ArrayList<StatusData>()
        val dir = Environment.getExternalStorageDirectory().toString() + File.separator + wAAPNormalFolder
        val dir2 = Environment.getExternalStorageDirectory().toString() + File.separator + wAPPBusinessFolder
        var file = File(dir)
        var file2 = File(dir2)
        if (file.exists() && file2.exists()) {
            try {
//                For Normal WAAAP
                var allFilesOfWAAPPNormalFolder = file.listFiles { dir, name ->
                    (name.endsWith("mp4")
                            || name.endsWith("3gp")
                            || name.endsWith("jpeg")
                            || name.endsWith("jpg")
                            || name.endsWith("png")
                            || name.endsWith("mpeg"))
                }
                for (imagePath in allFilesOfWAAPPNormalFolder) {
                    var path = StatusData()
                    path.media = (imagePath.absolutePath)
                    path.filename = (imagePath.name)
                    resultIAV.add(path)
                }

//                For Business WAAAP
                var allFilesWAAPPBusinessFolder =
                    file2.listFiles { dir, name ->
                        (name.endsWith(".mp4")
                                || name.endsWith(".3gp")
                                || name.endsWith(".jpeg")
                                || name.endsWith(".jpg")
                                || name.endsWith(".png")
                                || name.endsWith(".mpeg"))
                    }
                for (imagePath in allFilesWAAPPBusinessFolder) {
                    var path = StatusData()
                    path.media = (imagePath.absolutePath)
                    path.filename = (imagePath.name)
                    resultIAV.add(path)
                }
            } catch (e: NullPointerException) {
            }
        } else {
            if (file.exists()) {
                try {
                    var allFiles =
                        file.listFiles { dir, name ->
                            (name.endsWith(".mp4")
                                    || name.endsWith(".3gp")
                                    || name.endsWith(".jpeg")
                                    || name.endsWith(".jpg")
                                    || name.endsWith(".png")
                                    || name.endsWith(".mpeg"))
                        }
                    for (imagePath in allFiles) {
                        var path = StatusData()
                        path.media = (imagePath.absolutePath)
                        path.filename = (imagePath.name)
                        resultIAV.add(path)
                    }
                } catch (e: NullPointerException) {
                }
            } else {
                try {
                    var allFiles =
                        file2.listFiles { dir, name ->
                            (name.endsWith(".mp4")
                                    || name.endsWith(".3gp")
                                    || name.endsWith(".jpeg")
                                    || name.endsWith(".jpg")
                                    || name.endsWith(".png")
                                    || name.endsWith(".mpeg"))
                        }
                    for (imagePath in allFiles) {
                        var path = StatusData()
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

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Constant.SAVE_STATUS_INTENT) {
                if (getFilePaths()!!.size == 0) {
                    no_data_found_tv.setVisibility(View.VISIBLE)
                    status_rv.setVisibility(View.GONE)
                } else {
                    no_data_found_tv.setVisibility(View.GONE)
                    status_rv.setVisibility(View.VISIBLE)
                    adapter = RecyclerViewAdapter(
                        getFilePaths(),
                        (activity as Dashboard),
                        "StatusesFragment",
                        object : setOnitemClickListner {
                            override fun onLongClick(
                                view: View?,
                                mediaFile: String?,
                                position: Int
                            ) {

                            }
                        })
                    status_rv.setAdapter(adapter)
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

  /*  fun getFilePaths(): ArrayList<StatusData> {
        val list: ArrayList<StatusData> = ArrayList<StatusData>()
        val dir = Environment.getExternalStorageDirectory().toString() + File.separator + wAAPNormalFolder
        var file = File(dir)
        val listFile = file.listFiles()
        *//*if (listFile != null && listFile.isNullOrEmpty()) {
            Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE)
        }*//*
        if (listFile != null) {
            for (imgFile in listFile) {
                if (
                    imgFile.name.endsWith(".jpg")
                    || imgFile.name.endsWith(".jpeg")
                    || imgFile.name.endsWith(".png")
                ) {
                    val model : String = imgFile.absolutePath
                    var path = StatusData()
                    path.media = (model)
                    val filename = model.substring(model.lastIndexOf("/") + 1)
                    path.filename = (filename)
                    list.add(path)
                }
            }
        }

        // return imgPath List
        return list
    }
*/
    private val mActionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(
            mode: ActionMode,
            menu: Menu
        ): Boolean { // Inflate a menu resource providing context menu items
            val inflater = mode.menuInflater
//            inflater.inflate(R.menu.menu_multi_select, menu)
//            context_menu = menu
            return true
        }

        override fun onPrepareActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            return false // Return false if nothing is done
        }

        override fun onActionItemClicked(
            mode: ActionMode?,
            item: MenuItem?
        ): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(activity as Dashboard)
                .unregisterReceiver(broadcastReceiver)
        }
    }
}
