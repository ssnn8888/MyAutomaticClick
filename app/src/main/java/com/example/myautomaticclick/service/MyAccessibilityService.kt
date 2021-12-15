package com.example.myautomaticclick.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.myautomaticclick.util.sleep
import com.example.myautomaticclick.util.startDuokan
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MyAccessibilityService :
    AccessibilityService(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        Log.i("zhuwei", "AccessibilityEvent$p0")
//        val mAm = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val activityName = mAm.getRunningTasks(1)[0].topActivity!!.className
//        Log.i("zhuwei","activityName$activityName")
        val windoe = rootInActiveWindow
        Log.i("zhuwei", "windoe$windoe")
        val titleNode =
            rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.duokan.reader:id/general__header_view__center_title")
        if (titleNode != null && titleNode[0].text == "签到福利") {
            val receivedNode = rootInActiveWindow.findAccessibilityNodeInfosByText("已领取")
            val acceptNode = rootInActiveWindow.findAccessibilityNodeInfosByText("收下")
            Log.i("zhuwei", "receivedNode:${receivedNode}\nacceptNode${acceptNode}")
            // launch {
            if (receivedNode != null) {
                Log.i("zhuwei", "receivedNode")
                sleep(1000)
                clickNode(receivedNode)
                sleep(35000)
                startDuokan(this@MyAccessibilityService)
            }
            if (acceptNode != null) {
                Log.i("zhuwei", "acceptNode")
                sleep(1000)
                clickNode(acceptNode)
            }
            //  }
        }

    }


    private fun clickNode(node: List<AccessibilityNodeInfo>?) {
        if (node != null && node.isNotEmpty()) {
            node[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    private fun clickNode(node: AccessibilityNodeInfo?) {
        node?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    override fun onInterrupt() {
        Log.i("zhuwei", "onInterrupt")
    }

    override fun onCreate() {
        super.onCreate()
        job = Job()
        startDuokan(this)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

//    override fun onServiceConnected() {
//        val serviceInfo = AccessibilityServiceInfo().apply {
//            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
//            feedbackType  = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
//            packageNames = arrayOf("")
//            notificationTimeout  = 10
//            canRetrieveWindowContent
//        }
//        setServiceInfo(serviceInfo)
//    }
}