package com.example.myautomaticclick.util

import android.content.Context
import android.accessibilityservice.GestureDescription

import android.accessibilityservice.AccessibilityService.GestureResultCallback
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.annotation.SuppressLint

import android.annotation.TargetApi
import android.graphics.Path
import android.graphics.Point
import android.util.Log


/**
 * Copyright (c)2019 南京欣网互联网络科技有限公司
 *
 * @类描述:
 * @创建人: zw
 * @创建时间: 2021/12/15
 */
fun startDuokan(context: Context) {
    Log.i("zhuwei","startDuokan")
    context.startActivity(context.packageManager.getLaunchIntentForPackage("com.duokan.reader"))
}

fun sleep(time: Long) {
    Thread.sleep(time)
}


@SuppressLint("StaticFieldLeak")
var staticContext : Context? = null

