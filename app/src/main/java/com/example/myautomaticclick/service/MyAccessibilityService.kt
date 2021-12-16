package com.example.myautomaticclick.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.TargetApi
import android.app.Notification
import android.content.Intent
import android.graphics.Path
import android.graphics.Point
import android.os.Handler
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.myautomaticclick.util.sleep
import com.example.myautomaticclick.util.startDuokan
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import android.os.Parcelable
import com.example.myautomaticclick.MainActivity2


class MyAccessibilityService : AccessibilityService(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i("zhuwei", "AccessibilityEvent$event")
        val window = rootInActiveWindow
        Log.i("zhuwei", "window$window")
//        if (event?.eventType === AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
//            //获取事件具体信息
//            val parcelable = event.parcelableData
//            //如果是下拉通知栏消息
//            if (parcelable is Notification) {
//            } else {
//                //其它通知信息，包括Toast
//                val toastMsg = event!!.text[0] as String
//                Log.i("zhuwei", "onAccessibilityEvent: $toastMsg")
//                if (toastMsg.contains("尽快")) {
//                    startActivity(Intent(this, MainActivity2::class.java).apply {
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    })
//                    return
//                }
//            }
//        }
        val titleNode =
            rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.duokan.reader:id/general__header_view__center_title")
        if (titleNode != null && titleNode.size > 0 && titleNode[0].text == "签到福利") {
            sleep(1000)
            click(Point(940, 650))
            sleep(1000)
            click(Point(940, 650))

            Handler().postDelayed({
                startActivity(Intent(this, MainActivity2::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
            }, 35000)

//            val receivedNode = rootInActiveWindow.findAccessibilityNodeInfosByText("已领取")
//            val acceptNode = rootInActiveWindow.findAccessibilityNodeInfosByText("收下")
//            Log.i("zhuwei", "receivedNode:${receivedNode}\nacceptNode${acceptNode}")
//            // launch {
//            if (receivedNode != null) {
//                Log.i("zhuwei", "receivedNode")
//                sleep(1000)
//                //clickNode(receivedNode)
//                click(Point(940,650))
//                sleep(35000)
//                startDuokan(this@MyAccessibilityService)
//            }
//            if (acceptNode != null) {
//                Log.i("zhuwei", "acceptNode")
//                sleep(1000)
//                clickNode(acceptNode)
//            }
//            //  }
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

    @TargetApi(24)
    fun click(point: Point) {
        //只有7.0才可以用
        val builder = GestureDescription.Builder()
        val path = Path()
        path.moveTo(point.x.toFloat(), point.y.toFloat())
        path.lineTo(point.x.toFloat(), point.y.toFloat())
        /**
         * 参数path：笔画路径
         * 参数startTime：时间 (以毫秒为单位)，从手势开始到开始笔划的时间，非负数
         * 参数duration：笔划经过路径的持续时间(以毫秒为单位)，非负数
         */
        builder.addStroke(GestureDescription.StrokeDescription(path, 1, 1))
        val build = builder.build()
        /**
         * 参数GestureDescription：翻译过来就是手势的描述，如果要实现模拟，首先要描述你的腰模拟的手势嘛
         * 参数GestureResultCallback：翻译过来就是手势的回调，手势模拟执行以后回调结果
         * 参数handler：大部分情况我们不用的话传空就可以了
         * 一般我们关注GestureDescription这个参数就够了，下边就重点介绍一下这个参数
         */
        dispatchGesture(build, object : GestureResultCallback() {
            override fun onCancelled(gestureDescription: GestureDescription) {
                super.onCancelled(gestureDescription)
            }

            override fun onCompleted(gestureDescription: GestureDescription) {
                super.onCompleted(gestureDescription)
            }
        }, null)
    }

    override fun onInterrupt() {
        Log.i("zhuwei", "onInterrupt")
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("zhuwei", "onCreate")
        job = Job()
        startActivity(Intent(this, MainActivity2::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    override fun onDestroy() {
        Log.i("zhuwei", "onDestroy")
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