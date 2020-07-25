package com.paxees.wastatussaver.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.paxees.wastatussaver.R
import com.paxees.wastatussaver.Utils.Utils
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        toolbar.setTitle("")
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setOnClickListener(View.OnClickListener {
            finish()
        })
        privacyPolicyBtnID.setOnClickListener(this)
        termsOfUseBtnID.setOnClickListener(this)
        helpBtnID.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.privacyPolicyBtnID -> {
                var Intent = Intent(this, PrivacyPolicyActivity::class.java)
                startActivity(Intent)
            }
            R.id.termsOfUseBtnID -> {
                var Intent = Intent(this, TermOfUseActivity::class.java)
                startActivity(Intent)
            }
            R.id.helpBtnID -> {
            }

        }
    }

}
