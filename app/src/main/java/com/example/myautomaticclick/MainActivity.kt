package com.example.myautomaticclick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myautomaticclick.service.MyAccessibilityService
import android.text.TextUtils

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.provider.Settings
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_star).setOnClickListener {
            if (isAccessibilitySettingsOn(this,MyAccessibilityService::class.java)){
                startService(Intent(this,MyAccessibilityService::class.java))
            }else{
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
    }


    private fun isAccessibilitySettingsOn(
        mContext: Context,
        clazz: Class<out AccessibilityService?>
    ): Boolean {
        var accessibilityEnabled = 0
        val service: String = mContext.packageName.toString() + "/" + clazz.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                mContext.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        val mStringColonSplitter = SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue: String = Settings.Secure.getString(
                mContext.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService = mStringColonSplitter.next()
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}